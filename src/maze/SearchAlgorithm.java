package maze;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;

public class SearchAlgorithm {
    static class Node<T> {
        final T state;
        Node<T> parent;

        Node(T state, Node<T> parent) {
            this.state = state;
            this.parent = parent;
        }

        public static <T> List<T> nodeToPath(Node<T> node) {
            List<T> path = new ArrayList<>();
            while (node != null) {
                path.add(0, node.state);
                node = node.parent;
            }
            return path;
        }
    }

    static class HeuristicNode<T> extends Node<T> implements Comparable<HeuristicNode<T>> {
        double cost;
        double heuristic;

        HeuristicNode(T state, Node<T> parent, double cost, double heuristic) {
            super(state, parent);
            this.cost = cost;
            this.heuristic = heuristic;
        }

        @Override
        public int compareTo(HeuristicNode<T> o) {
            Double mine = cost + heuristic;
            Double others = o.cost + o.heuristic;
            return mine.compareTo(others);
        }
    }


    public static <T> Node<T> dfs(T initial, Predicate<T> predicate, Function<T, List<T>> successors) {
        if (initial == null) {
            return null;
        }
        LinkedList<Node<T>> stack = new LinkedList<>();
        stack.push(new Node<>(initial, null));
        Set<T> explored = new HashSet<>();
        // add here is a must ??
        explored.add(initial);
        while (!stack.isEmpty()) {
            Node<T> current = stack.pop();

            if (predicate.test(current.state)) {
                return current;
            }
            for (T successor : successors.apply(current.state)) {
                if (explored.contains(successor)) {
                    continue;
                }
                stack.push(new Node<>(successor, current));
                explored.add(successor);
            }
        }
        return null;
    }

    public static <T> Node<T> bfs(T initial, Predicate<T> predicate, Function<T, List<T>> successors) {
        if (initial == null) {
            return null;
        }
        LinkedList<Node<T>> queue = new LinkedList<>();
        queue.offer(new Node<>(initial, null));
        Set<T> explored = new HashSet<>();
        explored.add(initial);
        while (!queue.isEmpty()) {
            Node<T> current = queue.poll();

            if (predicate.test(current.state)) {
                return current;
            }
            for (T successor : successors.apply(current.state)) {
                if (explored.contains(successor)) {
                    continue;
                }
                queue.offer(new Node<>(successor, current));
                explored.add(successor);
            }
        }
        return null;
    }

    public static <T> Node<T> astar(T initial, Predicate<T> predicate, Function<T, List<T>> successors, ToDoubleFunction<T> heuristic) {
        if (initial == null) {
            return null;
        }
        PriorityQueue<HeuristicNode<T>> pq = new PriorityQueue<>();
        pq.offer(new HeuristicNode<>(initial, null, 0.0, heuristic.applyAsDouble(initial)));
        Map<T, Double> explored = new HashMap<>();
        explored.put(initial, 0.0);
        while (!pq.isEmpty()) {
            HeuristicNode<T> current = pq.poll();
            if (predicate.test(current.state)) {
                return current;
            }
            for (T successor : successors.apply(current.state)) {
                // naive approach
                double newCost = current.cost + 1;
                if (!explored.containsKey(successor) || explored.get(successor) > newCost) {
                    explored.put(successor, newCost);
                    pq.offer(new HeuristicNode<>(successor, current, newCost, heuristic.applyAsDouble(successor)));
                }
            }
        }
        return null;
    }
}


