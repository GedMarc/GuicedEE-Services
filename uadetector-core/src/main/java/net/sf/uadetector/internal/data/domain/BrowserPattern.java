/*******************************************************************************
 * Copyright 2012 André Rouél
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package net.sf.uadetector.internal.data.domain;

import net.sf.uadetector.internal.Check;
import net.sf.uadetector.internal.util.RegularExpressionConverter;

import java.io.Serializable;
import java.util.regex.Pattern;

/**
 * The {@code BrowserPattern} class represents the detection information for a browser specific item.<br>
 * <br>
 * A {@code BrowserPattern} object is immutable, their values cannot be changed after creation.
 *
 * @author André Rouél
 */

public final class BrowserPattern
		implements Identifiable, OrderedPattern<BrowserPattern>, Serializable
{

	private static final long serialVersionUID = 2845531314485836348L;
	/**
	 * Identification number (ID) of a browser pattern
	 */

	private final int id;
	/**
	 * A compiled representation of a regular expression to detect a browser
	 */
	@jakarta.validation.constraints.NotNull
	private final Pattern pattern;
	/**
	 * Position of a {@code BrowserPattern} (only relevant if there are multiple patterns for a browser in a
	 * {@code SortedSet})
	 */

	private final int position;

	public BrowserPattern(int id, @jakarta.validation.constraints.NotNull Pattern pattern, int position)
	{
		Check.notNegative(id, "id");
		Check.notNull(pattern, "pattern");
		Check.notNegative(position, "position");

		this.id = id;
		this.pattern = pattern;
		this.position = position;
	}

	/**
	 * Compares all attributes of this instance with the given instance of a {@code BrowserPattern}.
	 *
	 * <p>
	 * This method is <em>consistent with equals</em>.
	 *
	 * @param other
	 * 		another instance of {@code OperatingSystemPattern}
	 *
	 * @return negative value if one of the attributes of this instance is less, 0 if equal, or positive value if
	 * 		greater than the other one
	 */
	@SuppressWarnings("Duplicates")
	@Override
	public int compareTo(BrowserPattern other)
	{
		int result = other == null ? -1 : 0;
		if (result == 0)
		{
			result = compareInt(getPosition(), other.getPosition());
			if (result == 0)
			{
				result = compareInt(getId(), other.getId());
			}
			if (result == 0)
			{
				result = getPattern().pattern()
				                     .compareTo(other.getPattern()
				                                     .pattern());
			}
			if (result == 0)
			{
				result = compareInt(getPattern().flags(), other.getPattern()
				                                               .flags());
			}
		}
		return result;
	}

	/**
	 * Compares to integers.
	 *
	 * @param a
	 * 		first integer
	 * @param b
	 * 		second integer
	 *
	 * @return {@code -1} if {@code a} is less, {@code 0} if equal, or {@code 1} if greater than {@code b}
	 */
	private static int compareInt(int a, int b)
	{
		int result = 0;
		if (a > b)
		{
			result = 1;
		}
		else if (a < b)
		{
			result = -1;
		}
		return result;
	}

	/**
	 * Gets the identification number (ID) of a browser pattern.
	 *
	 * @return identification number (ID) of a browser pattern
	 */
	@Override
	public int getId()
	{
		return id;
	}

	@Override
	public Pattern getPattern()
	{
		return pattern;
	}

	@Override
	public int getPosition()
	{
		return position;
	}

	@Override
	public int hashCode()
	{
		int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + position;
		result = prime * result + pattern.pattern()
		                                 .hashCode();
		result = prime * result + pattern.flags();
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		BrowserPattern other = (BrowserPattern) obj;
		if (id != other.id)
		{
			return false;
		}
		if (position != other.position)
		{
			return false;
		}
		if (!pattern.pattern()
		            .equals(other.pattern.pattern()))
		{
			return false;
		}
		return pattern.flags() == other.pattern.flags();
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("BrowserPattern [id=");
		builder.append(id);
		builder.append(", pattern=");
		builder.append(pattern);
		builder.append(", position=");
		builder.append(position);
		builder.append("]");
		return builder.toString();
	}

	/**
	 * Factory that creates instances of {@code BrowserPattern} via method calls.
	 *
	 * @author André Rouél
	 */

	public static final class Builder
	{

		/**
		 * Identification number (ID) of a browser pattern
		 */
		private int id = Integer.MIN_VALUE;

		/**
		 * A compiled representation of a regular expression to detect a browser
		 */
		private Pattern pattern;

		/**
		 * Position of a {@code BrowserPattern} (only relevant if there are multiple patterns for a browser in a
		 * {@code SortedSet})
		 */
		private int position = Integer.MIN_VALUE;

		/**
		 * Builds a new instance of {@code BrowserPattern} and returns it.
		 *
		 * @return a new instance of {@code BrowserPattern}
		 *
		 * @throws net.sf.uadetector.exception.IllegalNegativeArgumentException
		 * 		if one of the needed arguments to build an instance of {@code BrowserPattern} is invalid
		 * 		<p>
		 * 		if one of the needed arguments to build an instance of {@code BrowserPattern} is invalid
		 */
		@jakarta.validation.constraints.NotNull
		public BrowserPattern build()
		{
			return new BrowserPattern(id, pattern, position);
		}

		/**
		 * Sets the identification number (ID) of a browser pattern. The given {@code String} is parsed as a decimal
		 * number.
		 *
		 * @param id
		 * 		ID of a browser pattern as string
		 *
		 * @return this {@code Builder}, for chaining
		 * 		<p>
		 * 		<p>
		 * 		if the given argument is {@code null}
		 *
		 * @throws NumberFormatException
		 * 		if the given string is not parsable as integer
		 * @throws net.sf.uadetector.exception.IllegalNegativeArgumentException
		 * 		if the parsed integer is smaller than {@code 0}
		 */
		@jakarta.validation.constraints.NotNull
		public Builder setId(@jakarta.validation.constraints.NotNull String id)
		{
			Check.notEmpty(id, "id");

			this.setId(Integer.parseInt(id.trim()));
			return this;
		}

		/**
		 * Sets the identification number of a browser pattern entry.
		 *
		 * @param id
		 * 		identification number
		 *
		 * @return this {@code Builder}, for chaining
		 *
		 * @throws net.sf.uadetector.exception.IllegalNegativeArgumentException
		 * 		if the given integer is smaller than {@code 0}
		 */
		@jakarta.validation.constraints.NotNull
		public Builder setId(int id)
		{
			Check.notNegative(id, "id");

			this.id = id;
			return this;
		}

		/**
		 * Converts a PERL regular expression in a Java regular expression and sets it in the {@code Builder}.
		 *
		 * @param regex
		 * 		PERL style regular expression to be converted
		 *
		 * @return this {@code Builder}, for chaining
		 */
		@jakarta.validation.constraints.NotNull
		public Builder setPerlRegularExpression(@jakarta.validation.constraints.NotNull String regex)
		{
			Check.notEmpty(regex, "regex");

			setPattern(RegularExpressionConverter.convertPerlRegexToPattern(regex));
			return this;
		}

		/**
		 * Sets a regular expression for a browser pattern.
		 *
		 * @param pattern
		 * 		compiled representation of a regular expression
		 *
		 * @return this {@code Builder}, for chaining
		 */
		@jakarta.validation.constraints.NotNull
		public Builder setPattern(@jakarta.validation.constraints.NotNull Pattern pattern)
		{
			Check.notNull(pattern, "pattern");

			this.pattern = pattern;
			return this;
		}

		/**
		 * Sets the position of a browser pattern in a set of patterns. The given {@code String} is parsed as a decimal
		 * number.
		 *
		 * @param position
		 * 		position of a browser pattern as string
		 *
		 * @return this {@code Builder}, for chaining
		 * 		<p>
		 * 		<p>
		 * 		if the given argument is {@code null}
		 *
		 * @throws NumberFormatException
		 * 		if the given string is not parsable as integer
		 * @throws net.sf.uadetector.exception.IllegalNegativeArgumentException
		 * 		if the parsed integer is smaller than {@code 0}
		 */
		@jakarta.validation.constraints.NotNull
		public Builder setPosition(@jakarta.validation.constraints.NotNull String position)
		{
			Check.notEmpty(position, "position");

			this.setPosition(Integer.parseInt(position.trim()));
			return this;
		}

		/**
		 * Sets the position of a browser pattern in a set of patterns.
		 *
		 * @param position
		 * 		position of a browser pattern
		 *
		 * @return this {@code Builder}, for chaining
		 *
		 * @throws net.sf.uadetector.exception.IllegalNegativeArgumentException
		 * 		if the given integer is smaller than {@code 0}
		 */
		@jakarta.validation.constraints.NotNull
		public Builder setPosition(int position)
		{
			Check.notNegative(position, "position");

			this.position = position;
			return this;
		}

	}

}
