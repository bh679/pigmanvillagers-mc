# Pigman Villagers

A Minecraft mod (Fabric / Forge / NeoForge, MC 1.21.1) that makes the occasional
villager a **pigman**. A pigman looks like a player with a pig's head and oinks
instead of hurring.

Part of the [Dungeon Train](https://github.com/bh679/dungeon-train-mc) family of mods.

## What changes

- Every villager has a **1%** chance to be a pigman. The roll happens once per
  villager and is saved with it. This covers villages, spawn eggs, breeding, cured
  zombie villagers and villagers placed by other mods.
- A pigman is drawn with the player model and the pigman skin, and holds its trade
  item in its hand.
- Its voice is a pig's. Ambient, trade and "yes" sounds become pig oinks, "no" and
  hurt sounds become pig hurt sounds, and its death sound is a pig's death sound.
  Job-site work sounds are unchanged.
- **Nothing else changes.** A pigman is still a normal villager, with the same
  professions, trades, gossip, breeding and raid behaviour. It isn't a new mob, so
  other mods that work with villagers keep working.

## Config

The config file is `config/pigmanvillagers.properties`:

```properties
# Chance (0.0 - 1.0) that a villager is a pigman. Rolled once per villager.
chance=0.01
```

Force one with a command:
`/summon minecraft:villager ~ ~ ~ {PigmanVillager:1b}`

## Install

Install it on **both the server and the client**. The pigman flag is synced entity
data, so a client without the mod can't join a server that has it.

## Building

```bash
./gradlew build
```

Jars land in `{fabric,forge,neoforge}/build/libs/pigmanvillagers-<loader>-<version>.jar`.
Unit tests: `./gradlew :common:test`.

## Credits

Pigman skin: "EmbeddedPigman" (<https://namemc.com/skin/51cd68df7bee7c88>).

## Licence

PolyForm Shield 1.0.0 — see `LICENSE`.
