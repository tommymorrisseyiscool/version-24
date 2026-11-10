# Rig Mod (Fabric 1.21.1)

Press **X** and the two nearest dispensers (within 6 blocks) get rigged:

- Left dispenser (relative to you) = diamonds, 1-9
- Right dispenser = totems, 1-9 (one per filled slot)
- Right number is random; left is always lower
- If right is 1, left is 1 (a tie counts as a loss)

## Notes
- Works in singleplayer or on a server you run yourself with the mod installed. Don't use it to cheat other players on servers you don't own.
- Rebind the key in Controls > Rig Mod.

## Build
**Easiest:** push to GitHub. The Actions workflow builds the jar; download it from the run's Artifacts.

**Locally:** install Java 21 and Gradle 8.8+, then run `gradle build`. The jar is in `build/libs/` (use the one without `-sources`).

Requires Fabric Loader 0.16+ and Fabric API for 1.21.1.
