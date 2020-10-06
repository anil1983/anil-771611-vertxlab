package com.ibm.rx.observable.transformation;

import java.util.Arrays;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public class MapUpperCase {
	
	 public static void main(String[] args) {
	        transform();
	    }

	    public static void transform() {
	    	
	    	 List<String> list = Arrays.asList("anil", "sunil", "banglore","delhi","jaipur","raj","cheenai");
	        Observable<String> stream = Observable.fromIterable(list).
	        		map(item -> {
	                            System.out.println("Before Upper Transform " + item);
	                            return item.toUpperCase();
	                        });
	      
	        stream.subscribe(data -> {
	            System.out.println("After Upper :" + data);
	        }, System.out::println, () -> {
	            System.out.println("Completed");
	        });
	    }

}
