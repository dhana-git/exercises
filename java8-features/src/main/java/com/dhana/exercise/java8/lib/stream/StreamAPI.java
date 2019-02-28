package com.dhana.exercise.java8.lib.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * <b>Stream API (java.util.stream)/ Streams framework</b><br>
 * <p>
 * A new library added into Java SE 8 for a functional-style operations on streams of elements.<br>
 * <br>
 * A stream represents a sequence of values or object references, and exposes a set of aggregate operations (mapTo,
 * filter, sum,..) that allow us to express common manipulations on those values easily and clearly.<br>
 * <br>
 * The libraries provide convenient ways to obtain stream views of collections, arrays, and other data sources.<br>
 * <br>
 * <ul>
 * <li>Stream operations are chained together into pipelines (think of fluent API).
 * <li>Streams don't have storage for values but carry values from a data source.
 * <li>Stream operations do not modify its underlying data source.
 * <li>Data source could be could be a data structure (Collection), a generating function, an I/O channel, etc.
 * <li>Streams do not provide a means to directly access or manipulate their elements, and are instead concerned with
 * declaratively describing the computational operations which will be performed in aggregate on that source.
 * <li>A stream is not finite, a stream pipeline with an infinite source can be infinite and can use short-circuiting
 * operations to terminate in finite time.
 * </ul>
 * </p>
 * <p>
 * <b>Streams Vs Collections</b> <br>
 * As an API, Streams is completely independent from Collections. While it is easy to use a collection as the source for
 * a stream (Collection has stream() and parallelStream() methods) or to dump the elements of a stream into a collection
 * (using the collect() operation as shown earlier), aggregates other than Collection can be used as sources for streams
 * as well. <br>
 * <br>
 * Many JDK classes, such as BufferedReader, Random, and BitSet, have been retrofitted to act as sources for streams,
 * and Arrays.stream() provides stream view of arrays. <br>
 * <br>
 * Anything that can be described with an Iterator can be used as a stream source.
 * </p>
 * <p>
 * <b>Laziness</b><br>
 * Stream operations (filter, map,..) can be performed eagerly () or lazily. <br>
 * <br>
 * The stream producing operations (such as filtering and mapping) are <b>"naturally lazy"</b>. On the other hand, the
 * value-producing operations such as sum(), or the side-effect producing operations such as forEach(), are
 * <b>"naturally eager"</b>, because they must produce a concrete result.
 * </p>
 * <p>
 * <b>Parallelism</b><br>
 * Stream pipelines can be executed in serial or parallel. <br>
 * <br>
 * Unless you explicitly ask for a parallel stream, the JDK implementations always return sequential streams. A
 * sequential stream may be converted into a parallel one with the parallel() method and vice versa.
 * </p>
 * 
 * @author DGOVINDAN
 */
public class StreamAPI {

    public static void main(String[] args) {

        { // Basic Stream
            List<String> list = new ArrayList<>();
            list.add("X");
            list.add("Y");
            list.add("Z");
            Stream<String> stream = list.stream(); // Sequential stream
            System.out.println("count:" + stream.count());
            System.out.println("isParallel:" + stream.isParallel());
        }
        {
            // Stream Operations
            /*-                    Terminal Operations
             * 
             * Stream Operations -|
             *                                               Stateful Operations
             *                    Intermediate Operations   -|
             *                                               Stateless Operations
             */
            /*
             * Stream Operations:
             * 
             * Stream operations are divided into intermediate and terminal operations, and are combined to form stream
             * pipelines.
             * 
             * A stream pipeline consists of a source (such as a Collection, an array, a generator function, or an I/O
             * channel); followed by zero or more intermediate operations such as Stream.filter or Stream.map; and a
             * terminal operation such as Stream.forEach or Stream.reduce.
             * 
             * Intermediate (lazy) vs Terminal (mostly eager) operations:
             * 
             * Intermediate operations return a new stream. They are always lazy; executing an intermediate operation
             * such as filter() does not actually perform any filtering, but instead creates a new stream that, when
             * traversed, contains the elements of the initial stream that match the given predicate. Traversal of the
             * pipeline source does not begin until the terminal operation of the pipeline is executed.
             * 
             * Terminal operations, such as Stream.forEach or IntStream.sum, may traverse the stream to produce a result
             * or a side-effect. After the terminal operation is performed, the stream pipeline is considered consumed,
             * and can no longer be used; if you need to traverse the same data source again, you must return to the
             * data source to get a new stream. In almost all cases, terminal operations are eager, completing their
             * traversal of the data source and processing of the pipeline before returning. Only the terminal
             * operations iterator() and spliterator() are not; these are provided as an "escape hatch" to enable
             * arbitrary client-controlled pipeline traversals in the event that the existing operations are not
             * sufficient to the task.
             * 
             * Stateful vs Stateless operations:
             * 
             * Stateless operations, such as filter and map, retain no state from previously seen element when
             * processing a new element -- each element can be processed independently of operations on other elements.
             * 
             * Stateful operations, such as distinct and sorted, may incorporate state from previously seen elements
             * when processing new elements.
             * 
             * Stateful operations may need to process the entire input before producing a result. For example, one
             * cannot produce any results from sorting a stream until one has seen all elements of the stream. As a
             * result, under parallel computation, some pipelines containing stateful intermediate operations may
             * require multiple passes on the data or may need to buffer significant data. Pipelines containing
             * exclusively stateless intermediate operations can be processed in a single pass, whether sequential or
             * parallel, with minimal data buffering.
             * 
             * Short-circuiting operations:
             * 
             * An intermediate operation is short-circuiting if, when presented with infinite input, it may produce a
             * finite stream as a result.
             * 
             * A terminal operation is short-circuiting if, when presented with infinite input, it may terminate in
             * finite time. Having a short-circuiting operation in the pipeline is a necessary, but not sufficient,
             * condition for the processing of an infinite stream to terminate normally in finite time.
             */

            IntStream stream = IntStream.iterate(8, arg -> arg << 2);
            System.out.println(stream.filter(arg -> arg % 8 == 0).limit(10).max().orElse(-1));

        }
    }

}
