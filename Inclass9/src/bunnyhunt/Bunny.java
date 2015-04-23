package bunnyhunt;

public class Bunny extends Animal {

    private boolean haveSeenWolf = false;
    private int distanceToWolf;
    private int directionToWolf;
    private int currentDirection;

    public Bunny(Model model, int row, int column) {
        super(model, row, column);
        currentDirection = Model.random(Model.MIN_DIRECTION,
                                        Model.MAX_DIRECTION);
    }
    
    @Override
    int decideMove() {
        // look for wolf
        haveSeenWolf = false;
        for (int i = Model.MIN_DIRECTION; i <= Model.MAX_DIRECTION; i++) {
            if (look(i) == Model.WOLF) {
                haveSeenWolf = true;
                directionToWolf = ((i + (Model.MAX_DIRECTION/2)) % (Model.MAX_DIRECTION+1));
                distanceToWolf = distance(i);
            }
        }
        
        // if wolf has been seen recently move away from its last known position
        if (haveSeenWolf) {
            if (distanceToWolf > 0) {
                distanceToWolf++;
                if (canMove(directionToWolf))
                    return directionToWolf;
                else if (canMove(Model.turn(directionToWolf, 1)))
                    return Model.turn(directionToWolf, 1);
                else if (canMove(Model.turn(directionToWolf, -1)))
                    return Model.turn(directionToWolf, -1);
                else {
                    currentDirection = Model.random(Model.MIN_DIRECTION,
                                                    Model.MAX_DIRECTION);
                    for (int i = 0; i < 8; i++) {
                        if (canMove(currentDirection))
                            return currentDirection;
                        else
                            currentDirection = Model.turn(currentDirection, 1);
                    }
                }
            }
        }
            
        // stuck! cannot move
        return Model.STAY;
    }
}


