# Product Engineer — Pigman Villagers

Sibling mod of the Dungeon Train family (AIN / AIS / PlayerMob / ECP / TE / Keep Trim / **Pigman Villagers**).

## Quick Reference

| | |
|---|---|
| Mod id | `pigmanvillagers` |
| Group | `games.brennan.pigmanvillagers` |
| Version | `gradle.properties` → `mod_version` |
| Build | `./gradlew build` |
| Tests | `./gradlew :common:test` |
| Key jars | `{fabric,forge,neoforge}/build/libs/pigmanvillagers-<loader>-<v>.jar` |
| Release | `gh workflow run release.yml -f tag=v<version>` (creates the tag; never tag manually) |
| Repo | `bh679/pigmanvillagers-mc` |

## What it does

Each villager rolls once (on its first server tick, default 1%, `config/pigmanvillagers.properties`)
to be a **pigman**: same villager, drawn as a player model wearing the pigman skin
(`assets/pigmanvillagers/textures/entity/pigman.png`, the legacy 64x32 NameMC skin "EmbeddedPigman"
converted to 64x64 the way vanilla converts legacy skins) and voiced with pig sounds. It is a flag
on vanilla `Villager`, deliberately NOT a new entity type — vanilla breeding/sensors check
`EntityType.VILLAGER`, and sibling mods (Trade Everything, Dungeon Train) match on it.

## Structure

- `common/` — all logic.
  - `PigmanConfig` — pure chance parsing + roll (unit-tested).
  - `sound/PigmanSoundTable` — pure villager→pig sound-path table (unit-tested); `PigmanSounds` resolves it.
  - `api/PigmanVillagersApi` — `isPigman(Entity)`, `setPigman(Villager, boolean)`.
  - `mixin/VillagerMixin` — synced `DATA_PIGMAN` + NBT `PigmanVillager`/`PigmanRolled` + first-tick roll.
  - `mixin/EntityPlaySoundMixin` — `@ModifyVariable` on `Entity.playSound(SoundEvent,FF)`; the one voice seam.
  - `mixin/VillagerRendererMixin` (client) — builds a companion `client/PigmanRenderer` from the same context.
  - `mixin/EntityRenderDispatcherMixin` (client) — `getRenderer` RETURN swaps in the pigman renderer.
- `fabric/`, `forge/`, `neoforge/` — thin entrypoints (`PigmanVillagers.init(configDir)`).

Synced entity data ⇒ required on both client and server.

## Standards

SemVer in `gradle.properties`: PATCH every commit, MINOR on release. bh679 Gate
workflow applies (see `~/.claude/` rules/playbooks). Releases only via release.yml
dispatch — it creates the tag, GitHub Release, and publishes to Modrinth/CurseForge
when `MODRINTH_PROJECT_ID`/`CURSEFORGE_PROJECT_ID` vars + tokens are set.

Dungeon Train consumes the **neoforge** jar via its shared `bh679` Ivy repo:
asset name MUST stay `pigmanvillagers-neoforge-<v>.jar` (flat, no `+mc` suffix).
