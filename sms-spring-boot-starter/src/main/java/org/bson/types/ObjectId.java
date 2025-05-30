/*
 * Copyright (C) 2022 [www.mobaijun.com]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.bson.types;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serial;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>
 * A globally unique identifier for objects.
 * </p>
 *
 * <p>
 * Consists of 12 bytes, divided as follows:
 * </p>
 * <table border="1">
 * <caption>ObjectID layout</caption>
 * <tr>
 * <td>0</td>
 * <td>1</td>
 * <td>2</td>
 * <td>3</td>
 * <td>4</td>
 * <td>5</td>
 * <td>6</td>
 * <td>7</td>
 * <td>8</td>
 * <td>9</td>
 * <td>10</td>
 * <td>11</td>
 * </tr>
 * <tr>
 * <td colspan="4">time</td>
 * <td colspan="5">random value</td>
 * <td colspan="3">inc</td>
 * </tr>
 * </table>
 *
 * <p>
 * Instances of this class are immutable.
 * </p>
 *
 * @mongodb.driver.manual core/object-id ObjectId
 * @see <a href=
 * "https://github.com/mongodb/mongo-java-driver/blob/master/bson/src/main/org/bson/types/ObjectId.java">origin</a>
 */
public final class ObjectId implements Comparable<ObjectId>, Serializable {

    // unused, as this class uses a proxy for serialization
    @Serial
    private static final long serialVersionUID = 1L;

    private static final int OBJECT_ID_LENGTH = 12;

    private static final int LOW_ORDER_THREE_BYTES = 0x00ffffff;

    // Use primitives to represent the 5-byte random value.
    private static final int RANDOM_VALUE1;

    private static final short RANDOM_VALUE2;

    private static final AtomicInteger NEXT_COUNTER = new AtomicInteger(new SecureRandom().nextInt());

    private static final char[] HEX_CHARS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
            'f'};

    static {
        try {
            SecureRandom secureRandom = new SecureRandom();
            RANDOM_VALUE1 = secureRandom.nextInt(0x01000000);
            RANDOM_VALUE2 = (short) secureRandom.nextInt(0x00008000);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * The timestamp
     */
    private final int timestamp;
    /**
     * The counter.
     */
    private final int counter;
    /**
     * the first four bits of randomness.
     */
    private final int randomValue1;
    /**
     * The last two bits of randomness.
     */
    private final short randomValue2;

    /**
     * Create a new object id.
     */
    public ObjectId() {
        this(new Date());
    }

    /**
     * Constructs a new instance using the given date.
     *
     * @param date the date
     */
    public ObjectId(final Date date) {
        this(dateToTimestampSeconds(date), NEXT_COUNTER.getAndIncrement() & LOW_ORDER_THREE_BYTES, false);
    }

    /**
     * Constructs a new instances using the given date and counter.
     *
     * @param date    the date
     * @param counter the counter
     * @throws IllegalArgumentException if the high order byte of counter is not zero
     */
    public ObjectId(final Date date, final int counter) {
        this(dateToTimestampSeconds(date), counter, true);
    }

    /**
     * Creates an ObjectId using the given time and counter.
     *
     * @param timestamp the time in seconds
     * @param counter   the counter
     * @throws IllegalArgumentException if the high order byte of counter is not zero
     */
    public ObjectId(final int timestamp, final int counter) {
        this(timestamp, counter, true);
    }

    private ObjectId(final int timestamp, final int counter, final boolean checkCounter) {
        this(timestamp, RANDOM_VALUE1, RANDOM_VALUE2, counter, checkCounter);
    }

    private ObjectId(final int timestamp, final int randomValue1, final short randomValue2, final int counter,
                     final boolean checkCounter) {
        if ((randomValue1 & 0xff000000) != 0) {
            throw new IllegalArgumentException(
                    "The random value must be between 0 and 16777215 (it must fit in three bytes).");
        }
        if (checkCounter && ((counter & 0xff000000) != 0)) {
            throw new IllegalArgumentException(
                    "The counter must be between 0 and 16777215 (it must fit in three bytes).");
        }
        this.timestamp = timestamp;
        this.counter = counter & LOW_ORDER_THREE_BYTES;
        this.randomValue1 = randomValue1;
        this.randomValue2 = randomValue2;
    }

    /**
     * Constructs a new instance from a 24-byte hexadecimal string representation.
     *
     * @param hexString the string to convert
     * @throws IllegalArgumentException if the string is not a valid hex string
     *                                  representation of an ObjectId
     */
    public ObjectId(final String hexString) {
        this(parseHexString(hexString));
    }

    /**
     * Constructs a new instance from the given byte array
     *
     * @param bytes the byte array
     * @throws IllegalArgumentException if array is null or not of length 12
     */
    public ObjectId(final byte[] bytes) {
        this(ByteBuffer.wrap(bytes));
    }

    /**
     * Constructs a new instance from the given ByteBuffer
     *
     * @param buffer the ByteBuffer
     * @throws IllegalArgumentException if the buffer is null or does not have at least 12
     *                                  bytes remaining
     * @since 3.4
     */
    public ObjectId(final ByteBuffer buffer) {
        if (buffer == null) {
            throw new IllegalArgumentException("buffer can not be null");
        }
        if (buffer.remaining() < OBJECT_ID_LENGTH) {
            throw new IllegalArgumentException("state should be: buffer.remaining() >=12");
        }

        // Note: Cannot use ByteBuffer.getInt because it depends on tbe buffer's byte
        // order
        // and ObjectId's are always in big-endian order.
        this.timestamp = makeInt(buffer.get(), buffer.get(), buffer.get(), buffer.get());
        this.randomValue1 = makeInt((byte) 0, buffer.get(), buffer.get(), buffer.get());
        this.randomValue2 = makeShort(buffer.get(), buffer.get());
        this.counter = makeInt((byte) 0, buffer.get(), buffer.get(), buffer.get());
    }

    /**
     * Gets a new object id.
     *
     * @return the new id
     */
    public static ObjectId get() {
        return new ObjectId();
    }

    /**
     * Gets a new object id with the given date value and all other bits zeroed.
     * <p>
     * The returned object id will compare as less than or equal to any other object id
     * within the same second as the given date, and less than any later date.
     * </p>
     *
     * @param date the date
     * @return the ObjectId
     * @since 4.1
     */
    public static ObjectId getSmallestWithDate(final Date date) {
        return new ObjectId(dateToTimestampSeconds(date), 0, (short) 0, 0, false);
    }

    /**
     * Checks if a string could be an {@code ObjectId}.
     *
     * @param hexString a potential ObjectId as a String.
     * @return whether the string could be an object id
     * @throws IllegalArgumentException if hexString is null
     */
    public static boolean isValid(final String hexString) {
        if (hexString == null) {
            throw new IllegalArgumentException();
        }

        int len = hexString.length();
        if (len != 24) {
            return false;
        }

        for (int i = 0; i < len; i++) {
            char c = hexString.charAt(i);
            if (c >= '0' && c <= '9') {
                continue;
            }
            if (c >= 'a' && c <= 'f') {
                continue;
            }
            if (c >= 'A' && c <= 'F') {
                continue;
            }

            return false;
        }

        return true;
    }

    private static byte[] parseHexString(final String s) {
        if (s == null) {
            throw new IllegalArgumentException("hexString can not be null");
        }
        if (s.length() != 24) {
            throw new IllegalArgumentException("state should be: hexString has 24 characters");
        }
        byte[] b = new byte[OBJECT_ID_LENGTH];
        for (int i = 0; i < b.length; i++) {
            int pos = i << 1;
            char c1 = s.charAt(pos);
            char c2 = s.charAt(pos + 1);
            b[i] = (byte) ((hexCharToInt(c1) << 4) + hexCharToInt(c2));
        }
        return b;
    }

    private static int hexCharToInt(final char c) {
        if (c >= '0' && c <= '9') {
            return c - 48;
        } else if (c >= 'a' && c <= 'f') {
            return c - 87;
        } else if (c >= 'A' && c <= 'F') {
            return c - 55;
        }
        throw new IllegalArgumentException("invalid hexadecimal character: [" + c + "]");
    }

    private static int dateToTimestampSeconds(final Date time) {
        return (int) (time.getTime() / 1000);
    }

    private static int makeInt(final byte b3, final byte b2, final byte b1, final byte b0) {
        // CHECKSTYLE:OFF
        return (((b3) << 24) | ((b2 & 0xff) << 16) | ((b1 & 0xff) << 8) | ((b0 & 0xff)));
        // CHECKSTYLE:ON
    }

    private static short makeShort(final byte b1, final byte b0) {
        // CHECKSTYLE:OFF
        return (short) (((b1 & 0xff) << 8) | ((b0 & 0xff)));
        // CHECKSTYLE:ON
    }

    private static byte int3(final int x) {
        return (byte) (x >> 24);
    }

    private static byte int2(final int x) {
        return (byte) (x >> 16);
    }

    private static byte int1(final int x) {
        return (byte) (x >> 8);
    }

    private static byte int0(final int x) {
        return (byte) (x);
    }

    private static byte short1(final short x) {
        return (byte) (x >> 8);
    }

    private static byte short0(final short x) {
        return (byte) (x);
    }

    /**
     * Convert to a byte array. Note that the numbers are stored in big-endian order.
     *
     * @return the byte array
     */
    public byte[] toByteArray() {
        ByteBuffer buffer = ByteBuffer.allocate(OBJECT_ID_LENGTH);
        putToByteBuffer(buffer);
        return buffer.array(); // using .allocate ensures there is a backing array that
        // can be returned
    }

    /**
     * Convert to bytes and put those bytes to the provided ByteBuffer. Note that the
     * numbers are stored in big-endian order.
     *
     * @param buffer the ByteBuffer
     * @throws IllegalArgumentException if the buffer is null or does not have at least 12
     *                                  bytes remaining
     * @since 3.4
     */
    public void putToByteBuffer(final ByteBuffer buffer) {
        if (buffer == null) {
            throw new IllegalArgumentException("buffer can not be null");
        }
        if (buffer.remaining() < OBJECT_ID_LENGTH) {
            throw new IllegalArgumentException("state should be: buffer.remaining() >=12");
        }

        buffer.put(int3(this.timestamp));
        buffer.put(int2(this.timestamp));
        buffer.put(int1(this.timestamp));
        buffer.put(int0(this.timestamp));
        buffer.put(int2(this.randomValue1));
        buffer.put(int1(this.randomValue1));
        buffer.put(int0(this.randomValue1));
        buffer.put(short1(this.randomValue2));
        buffer.put(short0(this.randomValue2));
        buffer.put(int2(this.counter));
        buffer.put(int1(this.counter));
        buffer.put(int0(this.counter));
    }

    /**
     * Gets the timestamp (number of seconds since the Unix epoch).
     *
     * @return the timestamp
     */
    public int getTimestamp() {
        return this.timestamp;
    }

    /**
     * Gets the timestamp as a {@code Date} instance.
     *
     * @return the Date
     */
    public Date getDate() {
        return new Date((this.timestamp & 0xFFFFFFFFL) * 1000L);
    }

    // Big-Endian helpers, in this class because all other BSON numbers are little-endian

    /**
     * Converts this instance into a 24-byte hexadecimal string representation.
     *
     * @return a string representation of the ObjectId in hexadecimal format
     */
    public String toHexString() {
        char[] chars = new char[OBJECT_ID_LENGTH * 2];
        int i = 0;
        for (byte b : toByteArray()) {
            chars[i++] = HEX_CHARS[b >> 4 & 0xF];
            chars[i++] = HEX_CHARS[b & 0xF];
        }
        return new String(chars);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ObjectId objectId = (ObjectId) o;

        if (this.counter != objectId.counter) {
            return false;
        }
        if (this.timestamp != objectId.timestamp) {
            return false;
        }

        if (this.randomValue1 != objectId.randomValue1) {
            return false;
        }

        if (this.randomValue2 != objectId.randomValue2) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = this.timestamp;
        result = 31 * result + this.counter;
        result = 31 * result + this.randomValue1;
        result = 31 * result + this.randomValue2;
        return result;
    }

    @Override
    public int compareTo(final ObjectId other) {
        if (other == null) {
            throw new NullPointerException();
        }

        byte[] byteArray = toByteArray();
        byte[] otherByteArray = other.toByteArray();
        for (int i = 0; i < OBJECT_ID_LENGTH; i++) {
            if (byteArray[i] != otherByteArray[i]) {
                return ((byteArray[i] & 0xff) < (otherByteArray[i] & 0xff)) ? -1 : 1;
            }
        }
        return 0;
    }

    @Override
    public String toString() {
        return toHexString();
    }

    /**
     * Write the replacement object.
     *
     * <p>
     * See https://docs.oracle.com/javase/6/docs/platform/serialization/spec/output.html
     * </p>
     *
     * @return a proxy for the document
     */
    private Object writeReplace() {
        return new SerializationProxy(this);
    }

    /**
     * Prevent normal deserialization.
     *
     * <p>
     * See https://docs.oracle.com/javase/6/docs/platform/serialization/spec/input.html
     * </p>
     *
     * @param stream the stream
     * @throws InvalidObjectException in all cases
     */
    private void readObject(final ObjectInputStream stream) throws InvalidObjectException {
        throw new InvalidObjectException("Proxy required");
    }

    private static class SerializationProxy implements Serializable {

        private static final long serialVersionUID = 1L;

        private final byte[] bytes;

        SerializationProxy(final ObjectId objectId) {
            this.bytes = objectId.toByteArray();
        }

        private Object readResolve() {
            return new ObjectId(this.bytes);
        }

    }
}
