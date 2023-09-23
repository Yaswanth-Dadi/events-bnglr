package reactiveexamples;

import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;

import java.io.IOException;

public class FluxExample {
    public static void main(String[] args) throws IOException {
        //ReactiveSources.intNumberMono().log().subscribe(integer -> System.out.println(integer));

        //Integer value = ReactiveSources.intNumberMono().block();

        //Subscribe to Flux
        ReactiveSources.userFlux().subscribe(user -> System.out.println(user.getFirstName()));

        /*List<User> users = ReactiveSources.userFlux().toStream().toList();
        for (User user : users) {
            System.out.println(user.getFirstName());
        }*/

        //Subscribe to Flux with do on next
        //ReactiveSources.userFlux()
        //      .subscribe(user -> System.out.println(user.getFirstName() + 1));

        //Subscribe to Flux with error handling and on next
        /*ReactiveSources.userFlux().doOnNext(user -> {
            if (user.getId() == 2)
                throw new RuntimeException("Invalid event");
        }).subscribe(user -> System.out.println(user.getFirstName()),
                error -> System.out.println(error.getMessage()));*/

        //Subscribe to Flux with on complete
        /*ReactiveSources.userFlux().subscribe(user -> System.out.println(user.getFirstName()),
                error -> System.out.println(error.getMessage()),
                () -> System.out.println("DONE"));*/

        //Subscribe to Flux with on subscribe
        /*ReactiveSources.userFlux().subscribe(user -> System.out.println(user.getFirstName()),
                err -> System.out.println(err.getMessage()),
                () -> System.out.println("DONE"),
                subscription -> subscription.request(2));*/

        //Back pressure
        /*ReactiveSources.userFlux().log().subscribe(new MySubscriber<>());*/

        /*ReactiveSources.intNumbersFlux()
                .filter(integer -> integer > 5)
                .log()
                .map(integer -> integer * 10)
                .subscribe(integer -> System.out.println(integer));*/

        System.out.println("Press a key to end");
        System.in.read();
    }
}

class MySubscriber<T> extends BaseSubscriber<T> {
    int total = 4;
    int batchLimit = 2;

    public void hookOnSubscribe(Subscription subscription) {
        System.out.println("Subscribed");
        subscription.request(batchLimit);
    }

    public void hookOnNext(T value) {
        System.out.println(value.toString() + "Received");

        //FLOW CONTROL
        total = total - 1;
        batchLimit = batchLimit - 1;

        if (total == 0) {
            cancel();
        } else if (batchLimit == 0) {
            System.out.println("NEW BATCH REQUESTED");
            request(2);
            batchLimit = 2;
        }
    }

    public void hookOnCancel() {
        System.out.println("Subscription cancelled");
    }

    public void hookOnComplete() {
        System.out.println("Completed");
    }
}
