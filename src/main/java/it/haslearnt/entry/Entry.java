package it.haslearnt.entry;

import it.haslearnt.cassandra.mapping.*;

import org.apache.commons.lang.builder.*;
import org.joda.time.Duration;

@Entity("Entries")
public class Entry extends EntityWithGeneratedId {

	public enum TimeType {
		MINUTES
	}

	@Column("skill")
	private String skill;

	@Column("when")
	private String when;

	@Column("difficulty")
	private String difficulty;

    @Column("skill_completed")
	private String completed = "false";

	private int learningTime;

	private TimeType timeType;

	@Column("points")
	private String points;

	public String what() {
		return skill;
	}

	public String when() {
		return when;
	}

	public String howDifficult() {
		return difficulty;
	}

	public Entry iveLearnt(String skill) {
		this.skill = skill;
		return this;
	}

	public Entry today() {
		return when("today");
	}

	public Entry andItWas(String difficulty) {
		this.difficulty = difficulty;
		return this;
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SIMPLE_STYLE);
	}

	public Entry when(String when) {
		this.when = when;
		return this;
	}

	public Entry build() {
		calculatePoints();
		return this;
	}

	private void calculatePoints() {
		this.points = Double.toString(learningTime * difficultyFactor());
	}

	private double difficultyFactor() {
		if ("easy".equals(difficulty))
			return 1;
		if ("medium".equals(difficulty))
			return 1.2;
		if ("hard".equals(difficulty))
			return 1.4;

		return 0;
	}

	public int points() {
		return (int) Double.valueOf(points).doubleValue();
	}

	public Entry itTook(int learningTime, TimeType timeType) {
		this.learningTime = learningTime;
		this.timeType = timeType;
		return this;
	}

	public Entry andIveCompleted() {
		this.completed = "true";
        return this;
	}

	public boolean isCompleted() {
        return Boolean.valueOf(this.completed);
    }

	public Duration getLearingDuration() {
		return Duration.standardMinutes(learningTime);
	}
}
