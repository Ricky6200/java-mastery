# W01 - Primitives & Wrappers: my doubts, cleared

## 1. `'A' + 1` compiles, `ch = ch + 1` doesn't

```java
char next = 'A' + 1;          // OK
char ch = 'A'; ch = ch + 1;   // COMPILE ERROR
```

- `char + int` always gives an **int**. Putting an int into a char is narrowing -> normally needs a cast.
- `'A' + 1` uses only **literals** -> compiler calculates 66 at compile time, sees it fits in a char, allows it.
- `ch` is a **variable** -> compiler can't know its value at compile time -> refuses.
- Proof: `final char ch = 'A'; char next = ch + 1;` compiles (final = constant).
- Fixes: `ch = (char) (ch + 1);` or `ch += 1;` or `ch++;` (compound operators cast automatically).

**Rule:** constant maths that fits is allowed; variable maths needs a cast.

## 2. Why `long result = 1_000_000 * 1_000_000;` gives `-727379968`

1. Both operands are `int` -> multiplication is done as **int** (left side `long` is ignored).
2. Real answer $10^{12}$ is bigger than int max (2,147,483,647).
3. int keeps only the lowest 32 bits: $10^{12} \bmod 2^{32} = 3{,}567{,}587{,}328$.
4. That is above int max, so it reads as negative: $3{,}567{,}587{,}328 - 4{,}294{,}967{,}296 = -727{,}379{,}968$.
5. Only then is the (already wrong) value widened to long.

Fix: `1_000_000L * 1_000_000`. **Type of a calculation comes from its operands, not the target variable.**

## 3. Wrapper classes - why primitives are not enough

| Primitive | Wrapper |
|---|---|
| byte, short, int, long | Byte, Short, **Integer**, Long |
| float, double | Float, Double |
| char, boolean | **Character**, Boolean |

Why we need them:
1. **Collections/generics need objects** - `List<int>` is invalid, `List<Integer>` works. (Biggest reason.)
2. **`null` = "no value"** - `int` can't say "missing". `Integer marks = null;` = absent student, DB `NULL`, empty CSV cell.
3. **Utility methods** - `Integer.parseInt("42")`, `Integer.MAX_VALUE`, `Character.isDigit('7')`, `Double.isNaN(x)`.
4. **APIs that take `Object`** - `Object o = 42;` works only via autoboxing.

Cost: `int` = 4 bytes, `Integer` = ~16 bytes object + reference, slower, can be null (NPE risk), `==` unsafe.
**Default to primitives; use wrappers only when you need an object or null.**

## 4. Ternary NPE

```java
Integer x = null;
int y = true ? x : 0;        // compiles, then NullPointerException at runtime
Integer z = true ? x : 0;    // ALSO NPE!
```

- One branch `Integer`, other `int` -> whole ternary becomes **int** -> `x.intValue()` on null -> NPE.
- Fix: `Integer z = true ? x : Integer.valueOf(0);` -> z = null, no exception.

## 5. Integer cache: why `127 == 127` is true

- The range **-128 to 127 includes 127** -> cached. 128 is just outside.

```java
Integer a = 127, b = 127;   a == b    // true  (same cached object)
Integer c = 128, d = 128;   c == d    // false (two new objects)
c.equals(d)                           // true  (same value)
```

**Cached** = created once in advance and reused. At startup Java makes 256 Integer objects (-128..127).
`Integer a = 127;` is compiled to `Integer.valueOf(127)`, which returns the pre-made object; for 128 it does `new Integer(128)`.

| Type | Cached |
|---|---|
| Byte, Short, Integer, Long | -128 to 127 |
| Character | 0 to 127 (ASCII) |
| Boolean | TRUE, FALSE |
| Float, Double | none - infinite decimals, no useful small set |

**Always compare wrappers with `.equals()`, never `==`.**

## 6. Interview answers

**When do you get an NPE from autoboxing?**
When Java has to **unbox a null** wrapper (it calls `.intValue()` on null). Common cases:
- `int a = x;` / `x + 1` / `x > 0` / `x == 5` (with a primitive)
- `int c = map.get("missing");` -> use `map.getOrDefault(key, 0)`
- mixed ternary `flag ? x : 0`
- `for (int n : listWithNulls)`

**Why not `double` for money?**
`double` is binary; 0.1 can't be stored exactly (like 1/3 = 0.333... in decimal), so small errors appear and add up.
`0.1 + 0.2` -> `0.30000000000000004`, `1.10 - 1.00` -> `0.10000000000000009`.
Use `new BigDecimal("0.10")` (String constructor! `new BigDecimal(0.1)` is already inaccurate) or store paise/cents in a `long`.

**Primitive vs reference variable?**

| | Primitive | Reference |
|---|---|---|
| Stores | the value | address of an object (heap) |
| null | no | yes |
| Default as field | 0 / false / '\u0000' | null |
| `==` compares | values | addresses |
| Methods / generics | no | yes |
| `b = a` | copies value (independent) | copies address (same object) |

Java is **always pass-by-value**: for references the *address* is copied, so a method can change the object but not make your variable point elsewhere.

## 7. Learned from NumberTraps

- `Math.round()` breaks .5 ties **towards positive infinity** (`floor(x + 0.5)`): `-2.5 -> -2`, `2.5 -> 3`, `-3.5 -> -3`. It is **not** round-to-even.
- `long big = 3_000_000_000;` -> compile error "integer number too large". Use uppercase **`L`** (lowercase `l` looks like `1`).
- `(byte) 200` -> `-56`: only the lowest 8 bits are kept (`200 - 256`).
- `'a' + 'b'` -> `195` (97 + 98, int). For text use `"" + 'a' + 'b'` -> `"ab"`.
- An uncaught exception stops `main`; wrap a crashing snippet in try/catch so later code still runs.
