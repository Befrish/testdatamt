package de.befrish.testdatamt.id.util;

import de.befrish.testdatamt.id.Identifiable;
import de.befrish.testdatamt.id.map.IdMap;
import de.befrish.testdatamt.id.map.IdMaps;
import de.befrish.testdatamt.id.map.MutableIdMap;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.util.Arrays;
import java.util.Objects;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Benno Müller
 */
public final class TestUtils {

    private TestUtils() {
        super();
    }

    @SafeVarargs
    public static <I extends Identifiable<?>> IdMap<I> idMapFromBuilders(final Builder<? extends I>... builders) {
        final IdMap<I> idMap = new MutableIdMap<>();
        for (final Builder<? extends I> builder : builders) {
            idMap.add(builder.build());
        }
        return IdMaps.immutable(idMap);
    }

    public static void assertEquals(final Object o1_1, final Object o1_2, final Object... os2) {
        assertThat(o1_1.equals(o1_2), is(true));
        assertThat(o1_1.equals(null), is(false));
        for (final Object o2 : os2) {
            assertThat(o1_1.equals(o2), is(false));
        }
    }

    public static void assertHashCode(final Object o1_1, final Object o1_2, final Object... os2) {
        assertThat(o1_1.hashCode(), is(o1_2.hashCode()));
        for (final Object o2 : os2) {
            assertThat(o1_1.hashCode(), is(not(o2.hashCode())));
        }
    }

    public static <I> Matcher<Identifiable<I>> equalsId(final I id) {
        return new EqualsIdMatcher<>(id);
    }

    public static <I> Matcher<IdMap<? extends Identifiable<I>>> hasId(final I id) {
        return new HasIdMatcher<>(id);
    }

    public static <I> Matcher<IdMap<? extends Identifiable<I>>> containsIds(final I... ids) {
        return new ContainsIdsMatcher<>(ids);
    }

    public static Matcher<String> equalsRegex(final String regex) {
        return new RegexMatcher(regex);
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    private static class EqualsIdMatcher<I> extends TypeSafeDiagnosingMatcher<Identifiable<I>> {

        private final I id;

        @Override
        protected boolean matchesSafely(final Identifiable<I> item, final Description mismatchDescription) {
            mismatchDescription.appendText("identifizierbares Objekt ")
                    .appendValue(item);
            return Objects.equals(id, item.getId());
        }

        @Override
        public void describeTo(final Description description) {
            description.appendText("identifizierbares Objekt mit ID ")
                    .appendValue(id);
        }

    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    private static class HasIdMatcher<I> extends TypeSafeDiagnosingMatcher<IdMap<? extends Identifiable<I>>> {

        private final I id;

        @Override
        protected boolean matchesSafely(final IdMap<? extends Identifiable<I>> item,
                final Description mismatchDescription) {
            mismatchDescription.appendText(IdMap.class.getSimpleName())
                    .appendText(" mit Elementen ")
                    .appendValue(item);
            return item.containsId(id);
        }

        @Override
        public void describeTo(final Description description) {
            description.appendText(IdMap.class.getSimpleName())
                    .appendText(" mit einem Element mit der ID ")
                    .appendValue(id);
        }

    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    private static class ContainsIdsMatcher<I> extends TypeSafeDiagnosingMatcher<IdMap<? extends Identifiable<I>>> {

        private final I[] ids;

        @Override
        protected boolean matchesSafely(
                final IdMap<? extends Identifiable<I>> item,
                final Description mismatchDescription) {
            mismatchDescription.appendText(IdMap.class.getSimpleName())
                    .appendText(" mit Elementen ")
                    .appendValue(item);
            return item.size() == ids.length
                    && contains(Arrays.stream(ids).map(TestUtils::equalsId).collect(toList()))
                    .matches(item.values());
        }

        @Override
        public void describeTo(final Description description) {
            description.appendText(IdMap.class.getSimpleName())
                    .appendText(" mit einem Element mit der IDs ")
                    .appendValue(ids);
        }

    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    private static class RegexMatcher extends TypeSafeDiagnosingMatcher<String> {

        private final String regex;

        @Override
        protected boolean matchesSafely(final String item, final Description mismatchDescription) {
            return item.matches(regex);
        }

        @Override
        public void describeTo(final Description description) {
            description.appendText(String.format("string matching regex \"%s\"", regex));
        }

    }

}
