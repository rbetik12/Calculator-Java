//package com.calcus;
//
//public class MainClass {
//    public static void main(String[] argv){
//        String[] formulas = new String[] { "2+2*2", "2+X*2", "sin(90)+4-cos(0)", "2--4", "6*x+6=0", "3.5.6-2" };
//        MatchParser p = new MatchParser();
//        p.setVariable("X", 2.0 );
//        for( int i = 0; i < formulas.length; i++){
//            try{
//                System.out.println( formulas[i] + "=" + p.Parse( formulas[i] ) );
//            }catch(Exception e){
//                System.err.println( "Error while parsing '"+formulas[i]+"' with message: " + e.getMessage() );
//            }
//        }
//    }
//}
