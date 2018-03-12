package com.mycompany.interviews;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Please code review this class: Add comments where you find clean code issues
public class WordDictionary implements IWordDictionary {

    List a;
    List<Action> callbacks = new ArrayList<>();
    private boolean isLoaded = false;

    @Override
    public void addCallback(Action callback)
    {
        callbacks.add(callback);
    }

    @Override
    public boolean isLoaded() {
        return isLoaded;
    }

    public void load(Action callback) {
        isLoaded = false;
        this.callbacks.add(callback);
        try {
            Path p = Paths.get("assets/wordlist.txt");

            ByteBuffer buffer = ByteBuffer.allocate(9999999);
            AsynchronousFileChannel channel = AsynchronousFileChannel.open(p);
            channel.read(buffer, 0, buffer, new CompletionHandler<Integer, ByteBuffer>() {
                @Override
                public void completed(Integer result, ByteBuffer attachment) {
                    buffer.flip();
                    byte[] data = new byte[buffer.limit()];
                    buffer.get(data);
                    String strData = new String(data);
                    a = Arrays.asList(strData.split("\\R")); // line separator (enter) characters
                    buffer.clear();
                    isLoaded = true;
                    for (Action cb : callbacks)
                    {
                        cb.execute();
                    }
                }
                @Override
                public void failed(Throwable exc, ByteBuffer attachment) {
                }
            });
        }
        catch (Exception ex) {
            System.out.println("something went wrong " + ex);
        }
    }


    public boolean contains(String word) {
        return a.contains(word);
    }

    public int size() {
        return a.size();
    }
}