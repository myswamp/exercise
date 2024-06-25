package recursion;

import java.util.LinkedList;


class NamedStack<E> extends LinkedList<E> {
    private String name;
    public NamedStack(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return this.name + ":" + super.toString();
    }
}

public class Hanoi {
    private int numOfDiscs;
    private NamedStack<Integer> stackA = new NamedStack<>("Stack A");
    private NamedStack<Integer> stackB = new NamedStack<>("Stack B");
    private NamedStack<Integer> stackC = new NamedStack<>("Stack C");

    public Hanoi(int numOfDiscs) {
        this.numOfDiscs = numOfDiscs;
        for (int i = numOfDiscs; i >= 1; i--) {
            stackA.push(i);
        }
    }

    public void solve() {
        move(stackA, stackC, stackB, 1);
    }

    public static void main(String[] args) {
        Hanoi hanoi = new Hanoi(3);
        System.out.println(hanoi.stackA);
        System.out.println(hanoi.stackB);
        System.out.println(hanoi.stackC);
        System.out.print(System.lineSeparator());
        hanoi.solve();
        System.out.print(System.lineSeparator());
        System.out.println(hanoi.stackA);
        System.out.println(hanoi.stackB);
        System.out.println(hanoi.stackC);
    }

    private void move(NamedStack<Integer> begin, NamedStack<Integer> end, NamedStack<Integer> temp, int discNum) {
        if (discNum == this.numOfDiscs) {
            int disc = begin.pop();
            end.push(disc);
            System.out.println(String.format("move disc %d from %s to %s", disc, begin.getName(), end.getName()));
        } else {
            move(begin, temp, end, discNum + 1);
            move(begin, end, temp, this.numOfDiscs);
            move(temp, end, begin, discNum + 1);
        }
    }
}
