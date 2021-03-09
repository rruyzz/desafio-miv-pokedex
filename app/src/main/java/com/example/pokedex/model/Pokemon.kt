package com.example.pokedex.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

data class PokemonResponse(
    @SerializedName("count")
    val count: Int,
    @SerializedName("next")
    val next: String?,
    @SerializedName("previous")
    val previous: String?,
    @SerializedName("results")
    val results: ArrayList<PokemonResult>
) : Serializable

data class PokemonResult(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
) :Serializable

data class Pokemon(
    val id: Int,
    val name: String,
    val baseExperience: Int,
    val height: Int,
    val isDefault: Boolean,
    val order: Int,
    val weight: Int,
    val species: PokemonResult,
    val forms: List<PokemonResult>,
    val abilities: List<PokemonAbility>,
    val gameIndices: List<VersionGameIndex>,
    val heldItems: List<PokemonHeldItem>,
    val moves: List<PokemonMove>,
    val stats: List<PokemonStat>,
    val types: List<PokemonType>,
    val sprites: PokemonSprites
)

data class Ability(
    val id: Int,
    val name: String,
    val isMainSeries: Boolean,
    val generation: PokemonResult,
    val names: List<Name>,
    val effectEntries: List<VerboseEffect>,
    val effectChanges: List<AbilityEffectChange>,
    val flavorTextEntries: List<AbilityFlavorText>,
    val pokemon: List<AbilityPokemon>
)

data class AbilityEffectChange(
    val effectEntries: List<Effect>,
    val versionGroup: PokemonResult
)

data class AbilityFlavorText(
    val flavorText: String,
    val language: PokemonResult,
    val versionGroup: PokemonResult
)

data class AbilityPokemon(
    val isHidden: Boolean,
    val slot: Int,
    val pokemon: PokemonResult
)

data class Characteristic(
    val id: Int,
    val geneModulo: Int,
    val possibleValues: List<Int>,
    val descriptions: List<Description>
)

data class EggGroup(
    val id: Int,
    val name: String,
    val names: List<Name>,
    val pokemonSpecies: List<PokemonResult>
)

data class Gender(
    val id: Int,
    val name: String,
    val pokemonSpeciesDetails: List<PokemonSpeciesGender>,
    val requiredForEvolution: List<PokemonResult>
)

data class PokemonSpeciesGender(
    val rate: Int,
    val pokemonSpecies: PokemonResult
)

data class GrowthRate(
    val id: Int,
    val name: String,
    val formula: String,
    val descriptions: List<Description>,
    val levels: List<GrowthRateExperienceLevel>,
    val pokemonSpecies: List<PokemonResult>
)

data class GrowthRateExperienceLevel(
    val level: Int,
    val experience: Int
)

data class Nature(
    val id: Int,
    val name: String,
    val decreasedStat: PokemonResult?,
    val increasedStat: PokemonResult?,
    val hatesFlavor: PokemonResult?,
    val likesFlavor: PokemonResult?,
    val pokeathlonStatChanges: List<NatureStatChange>,
    val moveBattleStylePreferences: List<MoveBattleStylePreference>,
    val names: List<Name>
)

data class NatureStatChange(
    val maxChange: Int,
    val pokeathlonStat: PokemonResult
)

data class MoveBattleStylePreference(
    val lowHpPreference: Int,
    val highHpPreference: Int,
    val moveBattleStyle: PokemonResult
)

data class PokeathlonStat(
    val id: Int,
    val name: String,
    val names: List<Name>,
    val affectingNatures: NaturePokeathlonStatAffectSets
)

data class NaturePokeathlonStatAffectSets(
    val increase: List<NaturePokeathlonStatAffect>,
    val decrease: List<NaturePokeathlonStatAffect>
)

data class NaturePokeathlonStatAffect(
    val maxChange: Int,
    val nature: PokemonResult
)

data class PokemonSprites(
    val backDefault: String?,
    val backShiny: String?,
    val frontDefault: String?,
    val frontShiny: String?,
    val backFemale: String?,
    val backShinyFemale: String?,
    val frontFemale: String?,
    val frontShinyFemale: String?

)

data class PokemonAbility(
    val isHidden: Boolean,
    val slot: Int,
    val ability: PokemonResult
)

data class PokemonHeldItem(
    val item: PokemonResult,
    val versionDetails: List<PokemonHeldItemVersion>
)

data class PokemonHeldItemVersion(
    val version: PokemonResult,
    val rarity: Int
)

data class PokemonMove(
    val move: PokemonResult,
    val versionGroupDetails: List<PokemonMoveVersion>
)

data class PokemonMoveVersion(
    val moveLearnMethod: PokemonResult,
    val versionGroup: PokemonResult,
    val levelLearnedAt: Int
)

data class PokemonStat(
    val stat: PokemonResult,
    val effort: Int,
    val baseStat: Int
)

data class PokemonType(
    val slot: Int,
    val type: PokemonResult
)

data class LocationAreaEncounter(
    val locationArea: PokemonResult,
    val versionDetails: List<VersionEncounterDetail>
)

data class PokemonColor(
    val id: Int,
    val name: String,
    val names: List<Name>,
    val pokemonSpecies: List<PokemonResult>
)

data class PokemonForm(
    val id: Int,
    val name: String,
    val order: Int,
    val formOrder: Int,
    val isDefault: Boolean,
    val isBattleOnly: Boolean,
    val isMega: Boolean,
    val formName: String,
    val pokemon: PokemonResult,
    val versionGroup: PokemonResult,
    val sprites: PokemonFormSprites,
    val formNames: List<Name>
)

data class PokemonFormSprites(
    val backDefault: String?,
    val backShiny: String?,
    val frontDefault: String?,
    val frontShiny: String?
)

data class PokemonHabitat(
    val id: Int,
    val name: String,
    val names: List<Name>,
    val pokemonSpecies: List<PokemonResult>
)

data class PokemonShape(
    val id: Int,
    val name: String,
    val awesomeNames: List<AwesomeName>,
    val names: List<Name>,
    val pokemonSpecies: List<PokemonResult>
)

data class AwesomeName(
    val awesomeName: String,
    val language: PokemonResult
)

data class PokemonSpecies(
    val id: Int,
    val name: String,
    val order: Int,
    val genderRate: Int,
    val captureRate: Int,
    val baseHappiness: Int,
    val isBaby: Boolean,
    val isLegendary: Boolean,
    val isMythical: Boolean,
    val hatchCounter: Int,
    val hasGenderDifferences: Boolean,
    val formsSwitchable: Boolean,
    val growthRate: PokemonResult,
    val pokedexNumbers: List<PokemonSpeciesDexEntry>,
    val eggGroups: List<PokemonResult>,
    val color: PokemonResult,
    val shape: PokemonResult,
    val evolvesFromSpecies: PokemonResult?,
//    val evolutionChain: ApiResource,
    val habitat: PokemonResult?,
    val generation: PokemonResult,
    val names: List<Name>,
    val palParkEncounters: List<PalParkEncounterArea>,
    val formDescriptions: List<Description>,
    val genera: List<Genus>,
    val varieties: List<PokemonSpeciesVariety>,
    val flavorTextEntries: List<PokemonSpeciesFlavorText>
)

data class PokemonSpeciesFlavorText(
    val flavorText: String,
    val language: PokemonResult,
    val version: PokemonResult
)

data class Genus(
    val genus: String,
    val language: PokemonResult
)

data class PokemonSpeciesDexEntry(
    val entryNumber: Int,
    val pokedex: PokemonResult
)

data class PalParkEncounterArea(
    val baseScore: Int,
    val rate: Int,
    val area: PokemonResult
)

data class PokemonSpeciesVariety(
    val isDefault: Boolean,
    val pokemon: PokemonResult
)

data class Stat(
    val id: Int,
    val name: String,
    val gameIndex: Int,
    val isBattleOnly: Boolean,
    val affectingMoves: MoveStatAffectSets,
    val affectingNatures: NatureStatAffectSets,
//    val characteristics: List<ApiResource>,
    val moveDamageClass: PokemonResult?,
    val names: List<Name>
)

data class MoveStatAffectSets(
    val increase: List<MoveStatAffect>,
    val decrease: List<MoveStatAffect>
)

data class MoveStatAffect(
    val change: Int,
    val move: PokemonResult
)

data class NatureStatAffectSets(
    val increase: List<PokemonResult>,
    val decrease: List<PokemonResult>
)

data class Type(
    val id: Int,
    val name: String,
    val damageRelations: TypeRelations,
    val gameIndices: List<GenerationGameIndex>,
    val generation: PokemonResult,
    val moveDamageClass: PokemonResult?,
    val names: List<Name>,
    val pokemon: List<TypePokemon>,
    val moves: List<PokemonResult>
)

data class TypePokemon(
    val slot: Int,
    val pokemon: PokemonResult
)

data class TypeRelations(
    val noDamageTo: List<PokemonResult>,
    val halfDamageTo: List<PokemonResult>,
    val doubleDamageTo: List<PokemonResult>,
    val noDamageFrom: List<PokemonResult>,
    val halfDamageFrom: List<PokemonResult>,
    val doubleDamageFrom: List<PokemonResult>
)

data class Name(
    val name: String,
    val language: PokemonResult
)

data class VersionGameIndex(
    val gameIndex: Int,
    val version: PokemonResult
)

data class VerboseEffect(
    val effect: String,
    val shortEffect: String,
    val language: PokemonResult
)

data class Effect(
    val effect: String,
    val language: PokemonResult
)

data class Description(
    val description: String,
    val language: PokemonResult
)

data class GenerationGameIndex(
    val gameIndex: Int,
    val generation: PokemonResult
)

data class VersionEncounterDetail(
    val version: PokemonResult,
    val maxChance: Int
//    val encounterDetails: List<Encounter>
)