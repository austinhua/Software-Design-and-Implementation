package game_engine.affectors;

import game_engine.game_elements.Unit;

public class RangeConstantPositionMoveAffector extends SingleTrackRangeAffector{
    
    PositionMoveAffector affector;
    
    public RangeConstantPositionMoveAffector (AffectorData data) {
        super(data);
        affector = new PositionMoveAffector(data);
    }

    @Override
    public void firstApply (Unit u, Unit tracked) {
        affector.apply(u);     
    }

    @Override
    public void futureApply (Unit u, Unit tracked) {
        firstApply(u, tracked);
    }

}
