/*
 * This file is generated by jOOQ.
 */
package com.example.scrapper.domain.jooq.tables.pojos;


import jakarta.validation.constraints.Size;

import java.beans.ConstructorProperties;
import java.io.Serializable;
import java.time.LocalDateTime;

import javax.annotation.processing.Generated;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.17.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Databasechangeloglock implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private Boolean locked;
    private LocalDateTime lockgranted;
    private String lockedby;

    public Databasechangeloglock() {}

    public Databasechangeloglock(Databasechangeloglock value) {
        this.id = value.id;
        this.locked = value.locked;
        this.lockgranted = value.lockgranted;
        this.lockedby = value.lockedby;
    }

    @ConstructorProperties({ "id", "locked", "lockgranted", "lockedby" })
    public Databasechangeloglock(
        @NotNull Integer id,
        @NotNull Boolean locked,
        @Nullable LocalDateTime lockgranted,
        @Nullable String lockedby
    ) {
        this.id = id;
        this.locked = locked;
        this.lockgranted = lockgranted;
        this.lockedby = lockedby;
    }

    /**
     * Getter for <code>public.databasechangeloglock.id</code>.
     */
    @jakarta.validation.constraints.NotNull
    @NotNull
    public Integer getId() {
        return this.id;
    }

    /**
     * Setter for <code>public.databasechangeloglock.id</code>.
     */
    public void setId(@NotNull Integer id) {
        this.id = id;
    }

    /**
     * Getter for <code>public.databasechangeloglock.locked</code>.
     */
    @jakarta.validation.constraints.NotNull
    @NotNull
    public Boolean getLocked() {
        return this.locked;
    }

    /**
     * Setter for <code>public.databasechangeloglock.locked</code>.
     */
    public void setLocked(@NotNull Boolean locked) {
        this.locked = locked;
    }

    /**
     * Getter for <code>public.databasechangeloglock.lockgranted</code>.
     */
    @Nullable
    public LocalDateTime getLockgranted() {
        return this.lockgranted;
    }

    /**
     * Setter for <code>public.databasechangeloglock.lockgranted</code>.
     */
    public void setLockgranted(@Nullable LocalDateTime lockgranted) {
        this.lockgranted = lockgranted;
    }

    /**
     * Getter for <code>public.databasechangeloglock.lockedby</code>.
     */
    @Size(max = 255)
    @Nullable
    public String getLockedby() {
        return this.lockedby;
    }

    /**
     * Setter for <code>public.databasechangeloglock.lockedby</code>.
     */
    public void setLockedby(@Nullable String lockedby) {
        this.lockedby = lockedby;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Databasechangeloglock other = (Databasechangeloglock) obj;
        if (this.id == null) {
            if (other.id != null)
                return false;
        }
        else if (!this.id.equals(other.id))
            return false;
        if (this.locked == null) {
            if (other.locked != null)
                return false;
        }
        else if (!this.locked.equals(other.locked))
            return false;
        if (this.lockgranted == null) {
            if (other.lockgranted != null)
                return false;
        }
        else if (!this.lockgranted.equals(other.lockgranted))
            return false;
        if (this.lockedby == null) {
            if (other.lockedby != null)
                return false;
        }
        else if (!this.lockedby.equals(other.lockedby))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
        result = prime * result + ((this.locked == null) ? 0 : this.locked.hashCode());
        result = prime * result + ((this.lockgranted == null) ? 0 : this.lockgranted.hashCode());
        result = prime * result + ((this.lockedby == null) ? 0 : this.lockedby.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Databasechangeloglock (");

        sb.append(id);
        sb.append(", ").append(locked);
        sb.append(", ").append(lockgranted);
        sb.append(", ").append(lockedby);

        sb.append(")");
        return sb.toString();
    }
}