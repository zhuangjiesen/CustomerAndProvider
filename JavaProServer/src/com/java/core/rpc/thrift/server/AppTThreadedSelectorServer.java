package com.java.core.rpc.thrift.server;

import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TNonblockingTransport;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.util.concurrent.BlockingQueue;

/**
 * Created by zhuangjiesen on 2017/6/16.
 */
public class AppTThreadedSelectorServer extends TThreadedSelectorServer {
    /**
     * Create the server with the specified Args configuration
     *
     * @param args
     */
    public AppTThreadedSelectorServer(Args args) {
        super(args);
    }


    @Override
    protected boolean startThreads() {
        return super.startThreads();
    }


    public class AppSelectorThread extends SelectorThread {

        public AppSelectorThread() throws IOException {
        }

        public AppSelectorThread(int maxPendingAccepts) throws IOException {
            super(maxPendingAccepts);
        }

        public AppSelectorThread(BlockingQueue<TNonblockingTransport> acceptedQueue) throws IOException {
            super(acceptedQueue);
        }





    }



    public class AppFrameBuffer extends FrameBuffer{

        public AppFrameBuffer(TNonblockingTransport trans, SelectionKey selectionKey, AbstractSelectThread selectThread) {
            super(trans, selectionKey, selectThread);
        }








    }





}
