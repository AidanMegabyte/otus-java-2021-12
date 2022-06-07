package ru.otus.service;

import io.grpc.stub.StreamObserver;
import ru.otus.protobuf.generated.NumberRangeRequest;
import ru.otus.protobuf.generated.NumberRangeServiceGrpc;
import ru.otus.protobuf.generated.NumberResponse;
import ru.otus.util.SleepUtils;

import java.util.Random;

public class NumberRangeServiceImpl extends NumberRangeServiceGrpc.NumberRangeServiceImplBase {

    @Override
    public void generate(NumberRangeRequest request, StreamObserver<NumberResponse> responseObserver) {
        var random = new Random();
        for (var i = request.getStart(); i <= request.getEnd(); i++) {
            responseObserver.onNext(NumberResponse.newBuilder().setNumber(i).build());
            SleepUtils.sleep(random.nextLong(5000) + 1);
        }
        responseObserver.onCompleted();
    }
}
