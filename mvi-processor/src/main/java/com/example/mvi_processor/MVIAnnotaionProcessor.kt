package com.example.mvi_processor

import com.example.mvi_annotations.MVIActions
import com.example.mvi_annotations.MVIResults
import com.example.mvi_annotations.MVIState
import com.example.mvi_annotations.StateTarget
import com.google.auto.service.AutoService
import me.eugeniomarletti.kotlin.processing.KotlinAbstractProcessor
import org.intellij.lang.annotations.Flow
import java.io.File
import javax.annotation.processing.Processor
import javax.lang.model.element.Element
import javax.lang.model.element.Name
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.ElementKind
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic


/**
 * This annotation processor should generate a file with Typealias like these:
 * typealias RdxSearchViewModel = MVIViewModel<SearchActions, SearchResults, SearchState>
 * typealias RdxSearchDispatcher = MVIDispatcher<SearchActions, SearchResults>
 */

private enum class Sequence { FIRST, SECOND, THIRD }

private typealias MVITriple = Triple<String?, String?, Pair<String?, StateTarget>?>

@AutoService(Processor::class)
class MVIAnnotaionProcessor : KotlinAbstractProcessor(){

    companion object {
        const val KOTLIN_DIRECTORY_NAME = "kapt.kotlin.generated"
        private val ACTIONS = MVIActions::class.java
        private val RESULTS = MVIResults::class.java
        private val STATE = MVIState::class.java
    }

    private var complete: Boolean = false
    private val map = HashMap<String, MVITriple>()
    private val elementImports = arrayListOf<String>()

    override fun process(annotations: Set<TypeElement>, roundEnv: RoundEnvironment): Boolean {
        if(complete) return false

        roundEnv.getElementsAnnotatedWith(ACTIONS)?.forEach {
            if (it.kind == ElementKind.CLASS) {
                val simpleName = it.simpleName.toString()
                val statePrefix = it.simpleName.statePrefix(Sequence.FIRST)
                elementImports.add(it.elementPackage())
                putOnMap(simpleName, statePrefix, Sequence.FIRST, StateTarget.FRAGMENT)
            }
        }
        roundEnv.getElementsAnnotatedWith(RESULTS)?.forEach {
            if (it.kind == ElementKind.CLASS) {
                val simpleName = it.simpleName.toString()
                val statePrefix = it.simpleName.statePrefix(Sequence.SECOND)
                elementImports.add(it.elementPackage())
                putOnMap(simpleName, statePrefix, Sequence.SECOND, StateTarget.FRAGMENT)
            }
        }
        roundEnv.getElementsAnnotatedWith(STATE)?.forEach {
            if (it.kind == ElementKind.CLASS) {
                val simpleName = it.simpleName.toString()
                val statePrefix = it.simpleName.statePrefix(Sequence.THIRD)
                elementImports.add(it.elementPackage())
                val targetName = try {
                    it.annotationMirrors[0].elementValues.values.first().value.toString()
                } catch (e: Exception) {
                    StateTarget.FRAGMENT.name
                }
                putOnMap(simpleName, statePrefix, Sequence.THIRD, StateTarget.valueOf(targetName))
            }
        }

        generateFile()//"br.com.xds.mvi.generated")
        return true
    }

    private fun putOnMap(
        simpleName: String,
        statePrefix: String,
        e: Sequence,
        target: StateTarget? = null
    ) {
        if(map.containsKey(statePrefix).not()) {
            when (e){
                Sequence.FIRST  -> map[statePrefix] = Triple(simpleName, null, null)
                Sequence.SECOND -> map[statePrefix] = Triple(null, simpleName, null)
                Sequence.THIRD  -> map[statePrefix] = Triple(null, null, Pair(simpleName, target!!))
            }
        } else {
            when (e){
                Sequence.FIRST  -> map[statePrefix] = map[statePrefix]!!.copy(first  = simpleName)
                Sequence.SECOND -> map[statePrefix] = map[statePrefix]!!.copy(second = simpleName)
                Sequence.THIRD  -> map[statePrefix] = map[statePrefix]!!.copy(third  = Pair(simpleName, target!!))
            }
        }
    }

    private fun generateFile() {
        val kaptKotlinGeneratedDir = options[KOTLIN_DIRECTORY_NAME]
        val file = File(kaptKotlinGeneratedDir, "TypeAliases.kt")

        var content = StringBuffer()
        content.append(
            "import br.com.xds.mvi.core.MVIDispatcher\n" +
            "import br.com.xds.mvi.core.MVIViewModel\n"  +
            "import br.com.xds.mvi.core.MVIActivity\n"  +
            "import br.com.xds.mvi.core.MVIFragment\n"
        )


        elementImports.forEach { content.append("import $it\n") }
        content.append("\n\n")

        map.forEach{ (prefix, triple) ->
            val error = hasAnyError(triple, prefix)
            if(error == null){
                content.append(vmTypealiasBy(prefix, triple))
                content.append(dTypealiasBy(prefix, triple))
                content.append(fTypealiasBy(prefix, triple) + "\n")
            } else {
                messager.printMessage(Diagnostic.Kind.ERROR, error)
            }
        }
        file.appendText(content.toString())
        complete = true
    }

    private fun hasAnyError(triple: MVITriple, prefix: String): String? {
        return when {
            triple.first  == null -> "MVIActions value is missing for $prefix"
            triple.second == null -> "MVIResults value is missing for $prefix"
            triple.third  == null -> "MVIState value is missing for $prefix"
            else -> null
        }
    }

    private fun fTypealiasBy(prefix: String, triple: MVITriple) =
        if(triple.third!!.second == StateTarget.FRAGMENT) {
            "typealias ${prefix}MVIFragment = MVIFragment<${triple.third!!.first}>\n"
        } else {
            "typealias ${prefix}MVIActivity = MVIActivity<${triple.third!!.first}>\n"
        }

    private fun dTypealiasBy(prefix: String, triple: MVITriple)
            = "typealias ${prefix}MVIDispatcher = MVIDispatcher<${triple.first}, ${triple.second}>\n"

    private fun vmTypealiasBy(prefix: String, triple: MVITriple)
            = "typealias ${prefix}MVIViewModel = MVIViewModel<${triple.first}, ${triple.second}, ${triple.third!!.first}>\n"


    override fun getSupportedAnnotationTypes(): Set<String> = setOf(STATE.canonicalName, ACTIONS.canonicalName, RESULTS.canonicalName)

    override fun getSupportedSourceVersion(): SourceVersion = SourceVersion.latest()

    private fun Element.elementPackage(): String {
        return "${elementUtils.getPackageOf(this)}.${this.simpleName}"
    }

}

private fun Name.statePrefix(e: Sequence): String {
    val oldVal = when(e){
        Sequence.FIRST  -> "Actio"
        Sequence.SECOND -> "Result"
        Sequence.THIRD  -> "State"
    }
    return this.toString().split(oldVal)[0]
}
