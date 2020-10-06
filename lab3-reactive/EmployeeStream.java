package com.ibm.rx.observable;

import io.reactivex.rxjava3.core.Observable;

class Employee{
	
	String name;
	String department;
	int empId;
	
	
	public Employee(String n, String d, int i) {
		name = n;
		department = d;
		empId = i;
		
	}
}

public class EmployeeStream {
	
	 public static void main(String[] args) {
	        //Publisher
	        Observable<Employee> stream = Observable.create(subscriber -> {
	            boolean isRightUser = true;
	            try {
	                if (isRightUser) {
	                    //start pushing data
	                    subscriber.onNext(new Employee("Raj", "GBS", 100));
	                    subscriber.onNext(new Employee("Aryan", "GTS", 200));
	                    subscriber.onComplete();
	                    

	                } else {
	                    throw new RuntimeException("Employee Terminated");

	                }

	            } catch (Exception e) {
	                subscriber.onError(e);
	                // data will not be send to subscriber because stream already closed
	                subscriber.onNext(new Employee("dummy", "GTS", 300));

	            }

	        });

	        //subscriber; listen for data,error,complete
	        stream.subscribe(data -> System.out.println(data.name), err -> System.out.println(err), () -> {
	            System.out.println("Completed");
	        });
	    }

}
