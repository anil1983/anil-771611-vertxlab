package com.ibm.vertx.future;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.example.util.Runner;

class Lab6FutureVerticle extends AbstractVerticle {

	public static String LOGIN_SUCCESS = "Login Success";
	public static String LOGIN_FAIL = "Login Failed";
	public static String namepwd = "admin";

	public Future<String> validateLogin(String username, String pwd) {
		// future object creation
		Future<String> future = Future.future();
		if (namepwd.equals(username) && namepwd.equals(pwd))
			future.complete(LOGIN_SUCCESS);
		else
			future.fail(new RuntimeException(LOGIN_FAIL));
		return future;
	}

	// sending future via Static factory
	public Future<String> validateLoginViaFactory(String username, String pwd) {
		if (namepwd.equals(username) && namepwd.equals(pwd)) {
			// encapuslate data , no data here
			return Future.succeededFuture(LOGIN_SUCCESS);
		} else {
			return Future.failedFuture(new RuntimeException(LOGIN_FAIL));
		}
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
		
		// register with sethandler ; annmous class
		validateLogin("admin","admin").setHandler(asyncResult -> {
			// grab result
			if (asyncResult.succeeded()) {
				System.out.println(asyncResult.result());
			}

			if (asyncResult.failed()) {
				System.out.println(asyncResult.cause());
			}
		});
		
		validateLogin("admin","invalid").onComplete(asyncResult -> {
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
		validateLogin("admin","invalid").onSuccess(System.out::println);
		/////////////////// handle failures
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
		validateLoginViaFactory("admin","admin").onSuccess(System.out::println).onFailure(System.out::println);

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

public class Lab6FutureMainVerticle extends AbstractVerticle {
	public static void main(String[] args) {
		Runner.runExample(Lab6FutureMainVerticle.class);
	}

	@Override
	public void start() throws Exception {
		super.start();
		vertx.deployVerticle(new Lab6FutureVerticle());
	}
}
