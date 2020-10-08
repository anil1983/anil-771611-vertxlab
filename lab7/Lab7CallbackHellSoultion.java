package com.ibm.vetx.callback;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.example.util.Runner;

class Lab7CallbackComposition extends AbstractVerticle {
  
	public Future<Boolean> prepareDatabase() {
    Promise<Boolean> promise = Promise.promise();
    //biz
    boolean isConnected = true;
    if (isConnected) {
      promise.complete(true);
    } else {
      promise.fail("Database Not Connected");;
    }

    return promise.future();
  }

  public Future<Boolean> startWebServer(boolean isConnected) {
	    Promise<Boolean> promise = Promise.promise();
	    //biz
	    boolean isServerReady = true;
	    if (isConnected) {
	      promise.complete(true);
	    } else {
	      promise.fail("WebServer Not Started");;
	    }

	    return promise.future();
	  }
  
  public Future<Boolean> startWebContainer(boolean isServerReady){
  Promise<Boolean> promise = Promise.promise();
  //biz
  if (isServerReady) {
    promise.complete(true);
  } else {
    promise.fail("WebContainer Not Started");;
  }

  return promise.future();
}

  public void callbackHellCode() {
	  prepareDatabase().onComplete(event -> {
      if (event.succeeded()) {
        System.out.println("prepareDatabase is called ");
        startWebServer(event.result()).onComplete(loginevent -> {
          if (loginevent.succeeded()) {
            System.out.println("startWebServer is called");
            startWebContainer(loginevent.result()).onComplete(pageevent -> {
              System.out.println("startWebContainer is called");
              if (pageevent.succeeded()) {
                System.out.println(pageevent.result());
              } else {
                System.out.println(pageevent.cause());
              }
            });
          } else {
            System.out.println(loginevent.cause());
          }
        });
      } else {
        System.out.println(event.cause());
      }
    });
  }
  //compose method; compose method is from future object.

  public void composeFuture() {
	  prepareDatabase().compose(userName -> {
      System.out.println("prepareDatabase is called ");
      return startWebServer(userName);
    }).compose(status -> {
      System.out.println("startWebServer is called");
      return startWebContainer(status);
    }).onComplete(asyncResult -> {
      System.out.println("startWebContainer is called");
      if (asyncResult.succeeded()) {
        System.out.println(asyncResult.result());
      } else {
        System.out.println(asyncResult.cause());
      }
    });
    //simple code , no log message;
	  prepareDatabase()
      .compose(userName -> startWebServer(userName))
      .compose(status -> startWebContainer(status))
      .onComplete(asyncResult -> {
        if (asyncResult.succeeded()) {
          System.out.println(asyncResult.result());
        } else {
          System.out.println(asyncResult.cause());
        }
      });
    //metho reference, onsuccess, onfailure
    prepareDatabase()
      .compose(this::startWebServer)
      .compose(this::startWebContainer)
      .onSuccess(System.out::println)
      .onFailure(System.out::println);
  }


  @Override
  public void start() throws Exception {
    super.start();
     callbackHellCode();
   // composeFuture();
  }
}


public class Lab7CallbackHellSoultion extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(Lab7CallbackHellSoultion.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    vertx.deployVerticle(new Lab7CallbackComposition());
  }
}
