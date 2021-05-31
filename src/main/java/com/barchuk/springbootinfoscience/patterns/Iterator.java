package com.barchuk.springbootinfoscience.patterns;

import java.util.Iterator;

class requestRepository implements Iterable<String> {

    private String[] requests;
    private int index;

    public requestRepository() {
        requests = new String[10];
        index = 0;
    }

    public void add(String request) {
        if (index == requests.length) {
            String[] largerrequests = new String[requests.length + 5];
            System.arraycopy(requests, 0, largerrequests, 0, requests.length);
            requests = largerrequests;
            largerrequests = null;
        }

        requests[index++] = request;
    }

    @Override
    public Iterator<String> iterator() {
        return new Iterator<String>() {
            private int currentIndex = 0;
            @Override
            public boolean hasNext() {
                return currentIndex < requests.length && requests[currentIndex] != null;
            }
            @Override
            public String next() {
                return requests[currentIndex++];
            }
            @Override
            public void remove() {
                System.arraycopy(requests, currentIndex + 1, requests,
                        currentIndex, requests.length - 1);
            }
        };
    }
}