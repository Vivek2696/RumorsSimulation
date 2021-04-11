package com.uofl.cse622;

import java.util.*;

public class Simulation {
    private Student[] students;
    private Map<Integer, ArrayList<Integer>> savedPairs;
    private int timer;

    public Simulation(int numberOfStudents){
        //Setting up students
        students = new Student[numberOfStudents];
        for(int i = 0; i < numberOfStudents; i++){
            students[i] = new Student();
        }
        //Setting one student to start the rumor from.
        students[0].setHasHeardRumor(true);
        students[0].increaseNumberOfTimesRumorHeard();

        //Setting other variables
        timer = 0;
        savedPairs = new HashMap<>();

        //settings up the empty pairs for each students
        for (Student student : students) {
            savedPairs.put(student.getId(), new ArrayList<>());
        }
    }

    public void startSimulation(int time, int percent){
        Map<Integer, Integer> pairs;
        int percentOfStudentHeardRumor = 0;
        while(!rumorSpreadAcrossAllStudents() && percentOfStudentHeardRumor < percent && timer < time){
            pairs = generatePairs();
            //System.out.println("Timer: "+timer+", Pairs formed: "+pairs.entrySet().size());
            for(Map.Entry<Integer, Integer> entry: pairs.entrySet()){
                simulateMeeting(entry.getKey(), entry.getValue());
            }
            int studentsHeardRumor = numberOfStudentsHeardRumors();
            percentOfStudentHeardRumor = (studentsHeardRumor * 100)/ (students.length);
            timer++;
        }
        printResult();
    }

    private Map<Integer, Integer> generatePairs(){
        Map<Integer, Integer> pairs = new HashMap<>();
        List<Integer> idList = new ArrayList<>();
        for(int i = 0; i < students.length; i++){
            idList.add(i);
        }
        Collections.shuffle(idList);
        while(!idList.isEmpty()){
            int firstStudentId = idList.remove(0);
            int secondStudentId = idList.get(0);
            boolean paired = false;
            int pairedIndex = 0;
            int tries = 0;
            //find partner
            while(!paired){
                if(pairedIndex >= idList.size()){
                    pairedIndex = idList.size() - 1;
                    tries = 100000;
                }
                secondStudentId = idList.get(pairedIndex);
                boolean found = true;
                ArrayList<Integer> ids = savedPairs.get(firstStudentId);
                for(int id: ids){
                    if(id == secondStudentId){
                        found = false;
                        break;
                    }
                }
                if(found){
                    paired = true;
                    idList.remove(pairedIndex);
                }else {
                    pairedIndex++;
                }
                if(tries > 10000){
                    //This is the exception where no more combination of pairs possible
                    //In this case just take next student as the partner!
                    paired = true;
                    secondStudentId = idList.remove(0);
                    System.out.println("Exception Occurred and Settled!");
                }
                tries++;
            }

            pairs.put(students[firstStudentId].getId(), students[secondStudentId].getId());
            updateSavedPairs(students[firstStudentId].getId(), students[secondStudentId].getId());
        }

        if(pairs.entrySet().size() == students.length /2){
            //System.out.println("All Paired!");
        } else {
            System.out.println("Error occurred in paring!");
        }

        return pairs;
    }

    private void simulateMeeting(int firstStudentId, int secondStudentId) {
        if(students[firstStudentId].hasHeardRumor() || students[secondStudentId].hasHeardRumor()){
            if(!students[firstStudentId].hasHeardRumor() && !students[secondStudentId].isStopSpreadingRumors()
                    && students[secondStudentId].isGoingToSpreadRumor()){
                students[firstStudentId].setHasHeardRumor(true);
                students[firstStudentId].increaseNumberOfTimesRumorHeard();
            }
            else if(!students[secondStudentId].hasHeardRumor() && !students[firstStudentId].isStopSpreadingRumors()
                        && students[firstStudentId].isGoingToSpreadRumor()){
                students[secondStudentId].setHasHeardRumor(true);
                students[secondStudentId].increaseNumberOfTimesRumorHeard();
            }
            else if (students[firstStudentId].hasHeardRumor() && students[secondStudentId].hasHeardRumor()){
                if(students[firstStudentId].isGoingToSpreadRumor()){
                    students[secondStudentId].increaseNumberOfTimesRumorHeard();
                }
                if(students[secondStudentId].isGoingToSpreadRumor()){
                    students[firstStudentId].increaseNumberOfTimesRumorHeard();
                }
            }
        }
    }

    private void printResult() {
        System.out.println("Time Elapsed: "+timer);
        System.out.println("Number of students heard rumors: "+numberOfStudentsHeardRumors());
        //Print the status of all students
        /*
        for(Student student: students){
            System.out.println("Id: "+student.getId()+", hasHeardRumor: "+student.isHasHeardRumor()+
                    ", stopSpreadingRumor: "+student.isStopSpreadingRumors());
        }
         */
    }

    private int numberOfStudentsHeardRumors() {
        int count = 0;
        for(Student student: students){
            if(student.hasHeardRumor()){
                count++;
            }
        }
        return count;
    }

    private void updateSavedPairs(int key, int newValue) {
        ArrayList<Integer> tempList = savedPairs.get(key);
        tempList.add(newValue);
        savedPairs.replace(key, tempList);
    }

    private boolean rumorSpreadAcrossAllStudents() {
        boolean rumorsSpreadAllAcross = true;
        for(Student student: students){
            if(!student.hasHeardRumor()){
                rumorsSpreadAllAcross = false;
                break;
            }
        }
        return rumorsSpreadAllAcross;
    }

    private void resetSavedPairs() {
        for(Student s: students){
            savedPairs.replace(s.getId(), new ArrayList<>());
        }
    }
}
