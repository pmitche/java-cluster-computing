package task;

import client.ClientTsp;
import system.CilkThread;
import system.Closure;
import system.Continuation;
import system.ResultValueWrapper;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by hallvard on 5/5/15.
 *
 * Uses Simmulated Annealing to solve the Traveling Salesman Problem through recursion deconstruction
 *
 * @deprecated  Not in use
 */
class TaskTspSa extends CilkThread {

    public TaskTspSa(Closure closure) {
        super(closure);
    }

    /**
     * Deconstructs the SA task to smaller instances
     * @param c Current continuation
     */
    @Override
    public void decompose(Continuation c) {

        AnnealingState state = (AnnealingState)c.argument;

        double temp = state.TEMP;
        int iteration = state.ITERATION;

        double thisDist = TspUtils.totalDistance(Arrays.asList(state.PATH));

        List<Integer[]> nextOrders = getNextPermutation(state.PATH);

        List<AnnealingState> succList = new ArrayList<>();
        for(Integer[] nextOrder : nextOrders) {
            double nextDist = TspUtils.totalDistance(Arrays.asList(nextOrder));
            double deltaDist =  nextDist-thisDist;

            if((deltaDist < 0) || (thisDist > 0
                    && Math.exp(-deltaDist/temp) > (new Random().nextDouble()*1.8))) {
                succList.add(new AnnealingState(nextOrder, temp * ClientTsp.COOLING_RATE, ++iteration));
            }
        }

        if(temp <= ClientTsp.ABS_TEMP || succList.size()==0){
            c.setReturnVal(new ResultValueWrapper<>(Arrays.asList(state.PATH), thisDist));
            //sendArgument(c);
            System.out.print("-");
            return;
        }

        String id = getId(succList.size(), c);

        //Spawn a new Continuation for each successor entry
        for(int i=0; i<succList.size(); i++) {
            spawn(new TaskTspSa(null), false, new Continuation(id, i + 1,succList.get(i)));
        }

//        System.out.println(state.TEMP);
    }

    /**
     *  Finds the SA instance with the best answer, and forwards it to parent thread.
     */
    @Override
    public void compose() {
        Continuation currCon = (Continuation)closure.getArgument(0);
        List<ResultValueWrapper> list = new ArrayList<>();

        int i=1;
        while(true) {
            try {
                list.add((ResultValueWrapper) closure.getArgument(i++));
            } catch (IndexOutOfBoundsException e) {
                break;
            }
        }

        ResultValueWrapper best = null;
        double shortest = Double.MAX_VALUE;
        for(ResultValueWrapper rvw : list) {
            if((Double)rvw.getN() < shortest) {
                best = rvw;
                shortest = (Double)rvw.getN();
            }
        }

        currCon.setReturnVal(best);
        //sendArgument(currCon);

        System.out.print(".");
   //     System.out.println(shortest);
    }

    /**
     * Create permutations of the input list.
     * @param current
     * @return
     */
    private List<Integer[]> getNextPermutation(Integer[] current) {
        List<Integer[]> list = new ArrayList<>();
        Random random = new Random();
        int bound = random.nextInt(current.length/3)+1;
        for(int i=0; i<bound; i++) {
            Integer[] permutation = current.clone();
            TspUtils.swap(permutation, random.nextInt(permutation.length), random.nextInt(permutation.length));
            list.add(permutation);
        }

        //Adding random permutation using Fisher-Yates shuffle
        Integer[] randPath = current.clone();
        TspUtils.shuffle(randPath);
        list.add(randPath);

        return list;
    }




    /**
     * Ugliest code ever written...
     *
     * @param size
     * @param c
     * @return
     */
    private String getId(int size, Continuation c) {

        String id = "-1";
        //Spawn next to get ID
        switch(size)
        {
            case 1: { id = spawnNext(new TaskTsp(null),false, c, null); break; }
            case 2: { id = spawnNext(new TaskTsp(null),false, c, null, null); break; }
            case 3: { id = spawnNext(new TaskTsp(null),false, c, null, null, null); break; }
            case 4: { id = spawnNext(new TaskTsp(null),false, c, null, null, null, null); break; }
            case 5: { id = spawnNext(new TaskTsp(null),false, c, null, null, null, null, null); break; }
            case 6: { id = spawnNext(new TaskTsp(null),false, c, null, null, null, null, null, null); break; }
            case 7: { id = spawnNext(new TaskTsp(null),false, c, null, null, null, null, null, null, null); break; }
            case 8: { id = spawnNext(new TaskTsp(null),false, c, null, null, null, null, null, null, null, null); break; }
            case 9: { id = spawnNext(new TaskTsp(null),false, c, null, null, null, null, null, null, null, null, null); break; }
            case 10: { id = spawnNext(new TaskTsp(null),false, c, null, null, null, null, null, null, null, null, null, null); break; }
            case 11: { id = spawnNext(new TaskTsp(null),false, c, null, null, null, null, null, null, null, null, null, null, null); break; }
            case 12: { id = spawnNext(new TaskTsp(null),false, c, null, null, null, null, null, null, null, null, null, null, null, null); break; }
            case 13: { id = spawnNext(new TaskTsp(null),false, c, null, null, null, null, null, null, null, null, null, null, null, null, null); break; }
            case 14: { id = spawnNext(new TaskTsp(null),false, c, null, null, null, null, null, null, null, null, null, null, null, null, null, null); break; }
            case 15: { id = spawnNext(new TaskTsp(null),false, c, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null); break; }
            case 16: { id = spawnNext(new TaskTsp(null),false, c, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null); break; }
            case 17: { id = spawnNext(new TaskTsp(null),false, c, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null); break; }
            default: {}
        }
        return id;
    }

}
