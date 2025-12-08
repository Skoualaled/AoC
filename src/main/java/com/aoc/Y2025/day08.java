package com.aoc.Y2025;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

import java.util.stream.Collectors;

public class day08 {

    private static final List<Jbox> jboxes = new ArrayList<>();
    private static final Logger logger = LogManager.getLogger(day08.class);
    private static final Integer connectionLimit = 1000;

    public static void main(String[] args) {
        logger.info("Start Day 07");
        var startTimer = Instant.now();
        File input = new File("src/main/resources/Y2025/day08.in");
        readFile(input);
        solve();
        var endTimer = Instant.now();
        logger.info("Execution time: {} ms", Duration.between(startTimer, endTimer).toMillis());
    }

    private static void readFile(File file) {
        try {
            Scanner input = new Scanner(file);
            while (input.hasNext()) {
                List<Integer> values = Arrays.stream(input.nextLine().split(",")).map(Integer::parseInt).toList();
                Jbox box = new Jbox(values.get(0), values.get(1), values.get(2));
                jboxes.add(box);
            }
        } catch (FileNotFoundException e) {
            logger.error("File not found");
            throw new RuntimeException(e);
        }
    }

    private static void solve(){
        Map<Set<Jbox>, Double> distMap = new HashMap<>(); // Pair de Box + Distance
        Map<Integer, Set<Jbox>> circuits = new HashMap<>(); // id + list des box ds circuit
        int resP1 =0;
        int resP2 =0;
        for(Jbox curBox : jboxes){
            List<Jbox> others = jboxes.stream().filter(b -> !b.equals(curBox)).toList();
            for(Jbox secondBox : others){
                Set<Jbox> boxList = Set.of(curBox, secondBox);
                if(!distMap.containsKey(boxList)){
                    distMap.put(boxList, distBoxes(curBox,secondBox));
                }
            }
        }
        // map sort by values
        distMap = distMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        int limit = 0;
        int MaxId = 0;
        for(Set<Jbox> distBox :distMap.keySet()){
            if (limit == connectionLimit) resP1 = solveP1(circuits);
            // circuit existant lie au 2 Jbox dans distBox
            List<Integer> curCircuit = new ArrayList<>();
            circuits.keySet().forEach(c -> distBox.forEach(b -> {
                if (circuits.get(c).contains(b)) {
                    curCircuit.add(c);
                }
            }));

            if (curCircuit.isEmpty()){ // Nouveau Circuit
                MaxId++;
                circuits.put(MaxId, new HashSet<>(distBox)) ;
            } else if (curCircuit.size()==1) { // Un seul Jbox dans un circuit
               circuits.get(curCircuit.getFirst()).addAll(distBox);
            } else if (curCircuit.getFirst()!=curCircuit.getLast()) { // JBox dans 2 cicuit different
                int cKey = curCircuit.getFirst();
                Set<Jbox> newCir = circuits.get(cKey);
                for (int i = 1; i < curCircuit.size(); i++) {
                    newCir.addAll(circuits.get(curCircuit.get(i)));
                    circuits.put(cKey, newCir);
                    circuits.remove(curCircuit.get(i));
                }
            }
            // Circuit unique atteint
            if(circuits.entrySet().stream().findFirst().get().getValue().size() == jboxes.size()){ // Circuit dde taille = nb de Jbox => Fin
                List<Jbox> res = distBox.stream().toList();
                resP2 = res.getFirst().x * res.getLast().x;
                break;
            }
            limit++;
        }
        logger.info("Part 1 : {}", resP1);
        logger.info("Part 2 : {}", resP2);
    }

    private static int solveP1( Map<Integer, Set<Jbox>> circuitsP1){
        Map<Integer, Integer> circuitSize = circuitsP1.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().size()));
        // logger.debug(circuitSize.toString());
        List<Integer> r = circuitSize.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new))
                .values().stream().toList();
        return r.getFirst()* r.get(1) * r.get(2);
    }

    private record Jbox(int x, int y, int z){}
    private static double distBoxes(Jbox f, Jbox s){
        return Math.sqrt(Math.pow(f.x()-s.x(),2) + Math.pow(f.y()-s.y(),2)+ Math.pow(f.z()-s.z(),2));
    }

}
