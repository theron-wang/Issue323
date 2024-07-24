package com.cogvio.time;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

public final class MutableClock extends Clock
{

    private Instant instant;
    private ZoneId zone;

    private MutableClock(final Instant instant, final ZoneId zone)
    {
        this.setZone(zone);
        this.setInstant(instant);
    }

    public void setTime(final LocalDateTime time)
    {
        setInstant(Objects.requireNonNull(time, "time must not be null").atZone(zone).toInstant());
    }

    public void setInstant(final Instant instant)
    {
        this.instant = Objects.requireNonNull(instant, "instant must not be null");
    }

    public void setZone(final ZoneId zone)
    {
        this.zone = Objects.requireNonNull(zone, "zone must not be null");
    }

    @Override
    public ZoneId getZone()
    {
        return zone;
    }

    @Override
    public MutableClock withZone(final ZoneId newZone)
    {
        if (newZone.equals(this.zone)) { // intentional NPE
            return this;
        }
        return new MutableClock(instant, newZone);
    }

    @Override
    public long millis()
    {
        return instant.toEpochMilli();
    }

    @Override
    public Instant instant()
    {
        return instant;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("instant", instant)
            .append("zone", zone)
            .toString();
    }

    public static MutableClock of(final LocalDateTime time)
    {
        return of(time, ZoneId.systemDefault());
    }

    public static MutableClock of(final LocalDateTime time, final ZoneId zone)
    {
        return new MutableClock(time.atZone(zone).toInstant(), zone);
    }

}