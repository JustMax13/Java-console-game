package MonsterAndDoor;

public class Monster implements IHaveUltimate, IDoDamage{
    private float _useUltimateChance;
    private float _damageDealt;

    @Override
    public float getChanceToUseUltimate() {
        return _useUltimateChance;
    }
    @Override
    public void setChanceToUseUltimate(float value) {
        _useUltimateChance = value;
    }
    @Override
    public float getDamageDealt() {
        return _damageDealt;
    }
    @Override
    public void setDamageDealt(float value) {
        _damageDealt = value;
    }

    public Monster(float damageDealt, float useUltimateChance){
        setDamageDealt(damageDealt);
        setChanceToUseUltimate(useUltimateChance);
    }

    @Override
    public void useUltimate() {
        setDamageDealt(getDamageDealt() + getDamageDealt() * 0.05f);
    }
}
