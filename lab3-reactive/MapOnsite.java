package com.ibm.rx.observable.transformation;

import java.util.Arrays;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public class MapOnsite {
	
	
	public static void main(String[] args) {
		
		 List<Employee2> list = Arrays.asList(new Employee2("Raj", true, 100),
				 new Employee2("B", false, 200),
				 new Employee2("C", false, 300),
				 new Employee2("D", false, 400),
				 new Employee2("E", true, 200),
				 new Employee2("F", true, 300));
		 
		 
				 Observable.fromIterable(list)
                //down stream, up stream
                .filter(item -> {
                    System.out.println("filtering " + item.name);
                    return item.onsite;
                })
                .subscribe(data -> {
                    System.out.println("Subscribe : " + data.name);
                }, System.out::println, () -> {
                    System.out.println("Completed");
                });
    }

}
