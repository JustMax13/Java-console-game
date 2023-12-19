package MonsterAndDoor;

public class SafetyDoor implements IHaveHitPoint, IHaveUltimate{
    private float _hitPoint;
    private float _useUltimateChance;

    @Override
    public float getHitPoint() {
        return _hitPoint;
    }
    @Override
    public void setHitPoint(float value) {
        _hitPoint = value;
    }
    @Override
    public float getChanceToUseUltimate() {
        return _useUltimateChance;
    }
    @Override
    public void setChanceToUseUltimate(float value) {
        _useUltimateChance = value;
    }

    public SafetyDoor(float hitPoint, float useUltimateChance){
        setHitPoint(hitPoint);
        setChanceToUseUltimate(useUltimateChance);
    }
    @Override
    public void useUltimate() {
        setHitPoint(getHitPoint() + getHitPoint() * 0.13f);
    }
}
