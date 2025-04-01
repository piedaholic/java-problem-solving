package com.piedaholic.multithreading;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public interface ProducerConsumer {
  int LIFE = 1000000;

  void run();

  static void main(String[] args) {
    SimpleProducerConsumerExample.invoke();
    QueueImpl.invoke();
    DataQueueImpl.invoke();
  }

  default void waitBeforeStopping() {
    long start = System.nanoTime();
    long stop = start;

    while (stop - start < LIFE) {
      stop = System.nanoTime();
    }
  }
}

abstract class Storage<T> {
  public final int size;
  protected final Queue<T> storage;

  Storage(int size) {
    this.size = size;
    this.storage = new LinkedList<>();
  }

  public void add(T message) {
    this.storage.add(message);
  }

  public T remove() {
    return this.storage.poll();
  }

  public boolean isEmpty() {
    return this.storage.isEmpty();
  }

  public boolean isFull() {
    return this.storage.size() >= this.size;
  }
}

class DataQueue<T> extends Storage<T> {

  private final Object IS_NOT_FULL = new Object();
  private final Object IS_NOT_EMPTY = new Object();

  DataQueue(int size) {
    super(size);
  }

  public void waitIsNotFull() throws InterruptedException {
    synchronized (IS_NOT_FULL) {
      IS_NOT_FULL.wait();
    }
  }

  public void notifyIsNotFull() {
    synchronized (IS_NOT_FULL) {
      IS_NOT_FULL.notify();
    }
  }

  public void waitIsNotEmpty() throws InterruptedException {
    synchronized (IS_NOT_EMPTY) {
      IS_NOT_EMPTY.wait();
    }
  }

  public void notifyIsNotEmpty() {
    synchronized (IS_NOT_EMPTY) {
      IS_NOT_EMPTY.notify();
    }
  }
}

interface Producer<T> {
  T produce();

  // Other methods
}

interface Consumer<T> {
  void consume(T t);

  // Other methods
}

interface RunnableProducer extends Runnable {

  void produce() throws InterruptedException;

  void stop() throws InterruptedException;

  @Override
  default void run() {
    try {
      this.produce();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}

interface RunnableConsumer extends Runnable {

  void consume() throws InterruptedException;

  void stop();

  @Override
  default void run() {
    try {
      this.consume();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}

class IntegerProducer implements Producer<Integer> {
  final Random random = new Random();

  @Override
  public Integer produce() {
    return random.nextInt();
  }
}

class GenericConsumer<T> implements Consumer<T> {
  @Override
  public void consume(T t) {
    System.out.println(t);
  }
}

class SimpleProducerConsumerExample implements ProducerConsumer {
  Producer<Integer> producer = new IntegerProducer();
  Consumer<Integer> consumer = new GenericConsumer<>();

  Integer data;
  boolean consume = false;

  public static void invoke() {
    ProducerConsumer instance = new SimpleProducerConsumerExample();
    instance.run();
  }

  @Override
  public void run() {
    try {
      RunnableProducer rp =
          new RunnableProducer() {
            boolean running = true;

            @Override
            public void produce() throws InterruptedException {
              while (running) {
                synchronized (SimpleProducerConsumerExample.this) {
                  while (consume) SimpleProducerConsumerExample.this.wait();
                  if (!running) {
                    break;
                  }
                  data = producer.produce();
                  // System.out.println("Generated: " + data);
                  consume = true;
                  SimpleProducerConsumerExample.this.notify();
                }
              }
            }

            public void stop() {
              running = false;
            }
          };

      RunnableConsumer rc =
          new RunnableConsumer() {
            boolean running = true;

            @Override
            public void consume() throws InterruptedException {
              while (running) {
                synchronized (SimpleProducerConsumerExample.this) {
                  while (!consume) SimpleProducerConsumerExample.this.wait();
                  if (!running) {
                    break;
                  }
                  // System.out.println("Consumed: " + data);
                  consumer.consume(data);
                  consume = false;
                  SimpleProducerConsumerExample.this.notify();
                }
              }
            }

            public void stop() {
              running = false;
            }
          };

      Thread producer = new Thread(rp);
      Thread consumer = new Thread(rc);

      producer.start();
      consumer.start();

      this.waitBeforeStopping();

      rp.stop();
      rc.stop();

      producer.join();
      consumer.join();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}

class QueueImpl implements ProducerConsumer {
  private static final int Q_MAX_SIZE = 100;
  Storage<Integer> queue = new Storage<>(Q_MAX_SIZE) {};

  public static void invoke() {
    ProducerConsumer instance = new QueueImpl();
    instance.run();
  }

  @Override
  public void run() {
    try {
      IntegerProducer integerProducer = new IntegerProducer();
      RunnableProducer rp =
          new RunnableProducer() {
            private boolean running = true;

            @Override
            public void produce() throws InterruptedException {
              while (running) {
                synchronized (QueueImpl.this) {
                  while (queue.isFull()) QueueImpl.this.wait();
                  if (!running) break;
                  queue.add(integerProducer.produce());
                  QueueImpl.this.notify();
                }
              }
            }

            @Override
            public void stop() {
              this.running = false;
              synchronized (QueueImpl.this) {
                QueueImpl.this.notify();
              }
            }
          };

      Consumer<Integer> gc = new GenericConsumer<>();
      RunnableConsumer rc =
          new RunnableConsumer() {
            private boolean running = true;

            @Override
            public void consume() throws InterruptedException {
              while (running) {
                synchronized (QueueImpl.this) {
                  while (queue.isEmpty()) QueueImpl.this.wait();
                  if (!running) break;
                  gc.consume(queue.remove());
                  QueueImpl.this.notify();
                }
              }
            }

            @Override
            public void stop() {
              this.running = false;
              synchronized (QueueImpl.this) {
                QueueImpl.this.notify();
              }
            }
          };

      Thread producerThread = new Thread(rp);
      Thread consumerThread = new Thread(rc);

      producerThread.start();
      consumerThread.start();

      this.waitBeforeStopping();

      rp.stop();
      rc.stop();

      producerThread.join();
      consumerThread.join();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}

class DataQueueImpl implements ProducerConsumer {
  private static final int Q_MAX_SIZE = 100;
  DataQueue<Integer> queue = new DataQueue<>(Q_MAX_SIZE) {};

  public static void invoke() {
    ProducerConsumer instance = new DataQueueImpl();
    instance.run();
  }

  @Override
  public void run() {
    try {
      IntegerProducer integerProducer = new IntegerProducer();
      RunnableProducer rp =
          new RunnableProducer() {
            private boolean running = true;

            @Override
            public void produce() throws InterruptedException {
              while (running) {
                if (queue.isFull()) queue.waitIsNotFull();
                if (!running) break;
                queue.add(integerProducer.produce());
                queue.notifyIsNotEmpty();
              }
            }

            @Override
            public void stop() {
              this.running = false;
              queue.notifyIsNotFull();
            }
          };

      Consumer<Integer> gc = new GenericConsumer<>();
      RunnableConsumer rc =
          new RunnableConsumer() {
            private boolean running = true;

            @Override
            public void consume() throws InterruptedException {
              while (running) {
                if (queue.isEmpty()) queue.waitIsNotEmpty();
                if (!running) break;
                gc.consume(queue.remove());
                queue.notifyIsNotFull();
              }
            }

            @Override
            public void stop() {
              this.running = false;
              queue.notifyIsNotEmpty();
            }
          };

      Thread producerThread = new Thread(rp);
      Thread consumerThread = new Thread(rc);

      producerThread.start();
      consumerThread.start();

      this.waitBeforeStopping();

      rp.stop();
      rc.stop();

      producerThread.join();
      consumerThread.join();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
