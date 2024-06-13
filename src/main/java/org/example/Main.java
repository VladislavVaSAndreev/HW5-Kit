package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        RingTable ringTable = new RingTable(5);

        try {
            ringTable.startEating();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
