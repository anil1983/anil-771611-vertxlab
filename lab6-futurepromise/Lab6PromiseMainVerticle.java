package com.ibm.vertx.future;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Promise;
import io.vertx.example.util.Runner;

class Lab6PromiseVerticle extends AbstractVerticle {

	public static String LOGIN_SUCCESS = "Login Success";
	public static String LOGIN_FAIL = "Login Failed";
	public static String namepwd = "admin";

	public Future<String> validateLogin(String username, String pwd) {
		// future object creation
		 Promise<String> promise = Promise.promise();
		if (namepwd.equals(username) && namepwd.equals(pwd))
			promise.complete(LOGIN_SUCCESS);
		else
			promise.fail(new RuntimeException(LOGIN_FAIL));
		return promise.future();
	}

	
	// function as a parameter pattern ; callback pattern
	public void isValid(String username, String pwd, Handler<AsyncResult<String>> asyncResultHandler) {
		if (namepwd.equals(username) && namepwd.equals(pwd)) {
			// encapuslate data into future.
			asyncResultHandler.handle(Future.succeededFuture(LOGIN_SUCCESS));
		} else {
			asyncResultHandler.handle(Future.failedFuture(LOGIN_FAIL));
		}

	}

	@Override
	public void start() throws Exception {
		super.start();
		
		validateLogin("admin","admin").onComplete(asyncResult -> {
			// grab result
						if (asyncResult.succeeded()) {
							System.out.println(asyncResult.result());
						}

						if (asyncResult.failed()) {
							System.out.println(asyncResult.cause());
						}
		});
		validateLogin("admin","invalid").onSuccess(response -> {
			System.out.println(response);
		});
		validateLogin("admin","invalid").onComplete(asyncResult -> {
			if (asyncResult.failed()) {
				System.out.println(asyncResult.cause());
			}
		});
		validateLogin("admin","invalid").onFailure(System.out::println);
		//////////////////////////////////////////////////////////////////////////////////////////
		validateLogin("admin","invalid").onComplete(asyncResult -> {
			if (asyncResult.succeeded()) {
				System.out.println(asyncResult.result());
			} else {
				System.out.println(asyncResult.cause());
			}
		});
		
		validateLogin("admin","admin").onSuccess(System.out::println).onFailure(System.out::println);

		// function as a parameter
		isValid("admin","admin", asyncResult -> {
			if (asyncResult.succeeded()) {
				System.out.println(asyncResult.result());
			} else {
				System.out.println(asyncResult.cause());
			}

		});

	}
}

public class Lab6PromiseMainVerticle extends AbstractVerticle {
	public static void main(String[] args) {
		Runner.runExample(Lab6PromiseMainVerticle.class);
	}

	@Override
	public void start() throws Exception {
		super.start();
		vertx.deployVerticle(new Lab6FutureVerticle());
	}
}
