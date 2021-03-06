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

import net.sf.uadetector.UserAgent;
import net.sf.uadetector.UserAgentFamily;
import net.sf.uadetector.UserAgentType;
import net.sf.uadetector.internal.Check;

import java.io.Serializable;


public final class Robot
		implements Identifiable, Serializable
{

	/**
	 * Default type name to support the classification against corresponding enum later
	 */
	public static final String TYPENAME = "Robot";
	private static final long serialVersionUID = -605392434061575985L;
	@jakarta.validation.constraints.NotNull
	private final UserAgentFamily family;
	@jakarta.validation.constraints.NotNull
	private final String familyName;
	private final int hash;
	@jakarta.validation.constraints.NotNull
	private final String icon;

	private final int id;
	@jakarta.validation.constraints.NotNull
	private final String infoUrl;
	@jakarta.validation.constraints.NotNull
	private final String name;
	@jakarta.validation.constraints.NotNull
	private final String producer;
	@jakarta.validation.constraints.NotNull
	private final String producerUrl;
	@jakarta.validation.constraints.NotNull
	private final String userAgentString;

	public Robot(int id, @jakarta.validation.constraints.NotNull String name, @jakarta.validation.constraints.NotNull UserAgentFamily family,
	             @jakarta.validation.constraints.NotNull String familyName,
	             @jakarta.validation.constraints.NotNull String infoUrl, @jakarta.validation.constraints.NotNull String producer,
	             @jakarta.validation.constraints.NotNull String producerUrl,
	             @jakarta.validation.constraints.NotNull String userAgentString, @jakarta.validation.constraints.NotNull String icon)
	{
		this.id = Check.notNegative(id, "id");
		this.name = Check.notNull(name, "name");
		this.family = Check.notNull(family, "family");
		this.familyName = Check.notNull(familyName, "familyName");
		this.infoUrl = Check.notNull(infoUrl, "infoUrl");
		this.producer = Check.notNull(producer, "producer");
		this.producerUrl = Check.notNull(producerUrl, "producerUrl");
		this.userAgentString = Check.notNull(userAgentString, "userAgentString");
		this.icon = Check.notNull(icon, "icon");
		hash = buildHashCode(id, name, family, familyName, infoUrl, producer, producerUrl, userAgentString, icon);
	}

	private static int buildHashCode(
			int id, @jakarta.validation.constraints.NotNull String name, @jakarta.validation.constraints.NotNull UserAgentFamily family,
			@jakarta.validation.constraints.NotNull String familyName,
			@jakarta.validation.constraints.NotNull String infoUrl, @jakarta.validation.constraints.NotNull String producer,
			@jakarta.validation.constraints.NotNull String producerUrl,
			@jakarta.validation.constraints.NotNull String userAgentString, @jakarta.validation.constraints.NotNull String icon)
	{
		int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + name.hashCode();
		result = prime * result + family.hashCode();
		result = prime * result + familyName.hashCode();
		result = prime * result + infoUrl.hashCode();
		result = prime * result + producer.hashCode();
		result = prime * result + producerUrl.hashCode();
		result = prime * result + userAgentString.hashCode();
		result = prime * result + icon.hashCode();
		return result;
	}

	public void copyTo(@jakarta.validation.constraints.NotNull UserAgent.Builder builder)
	{
		builder.setFamily(family);
		builder.setIcon(icon);
		builder.setName(name);
		builder.setProducer(producer);
		builder.setProducerUrl(producerUrl);
		builder.setUrl(infoUrl);
		builder.setType(UserAgentType.ROBOT);
	}

	@jakarta.validation.constraints.NotNull
	public UserAgentFamily getFamily()
	{
		return family;
	}

	@jakarta.validation.constraints.NotNull
	public String getFamilyName()
	{
		return familyName;
	}

	@jakarta.validation.constraints.NotNull
	public String getIcon()
	{
		return icon;
	}

	@Override

	public int getId()
	{
		return id;
	}

	@jakarta.validation.constraints.NotNull
	public String getInfoUrl()
	{
		return infoUrl;
	}

	@jakarta.validation.constraints.NotNull
	public String getName()
	{
		return name;
	}

	@jakarta.validation.constraints.NotNull
	public String getProducer()
	{
		return producer;
	}

	@jakarta.validation.constraints.NotNull
	public String getProducerUrl()
	{
		return producerUrl;
	}

	@jakarta.validation.constraints.NotNull
	public String getUserAgentString()
	{
		return userAgentString;
	}

	@Override
	public int hashCode()
	{
		return hash;
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
		Robot other = (Robot) obj;
		if (id != other.id)
		{
			return false;
		}
		if (!name.equals(other.name))
		{
			return false;
		}
		if (!family.equals(other.family))
		{
			return false;
		}
		if (!familyName.equals(other.familyName))
		{
			return false;
		}
		if (!infoUrl.equals(other.infoUrl))
		{
			return false;
		}
		if (!producer.equals(other.producer))
		{
			return false;
		}
		if (!producerUrl.equals(other.producerUrl))
		{
			return false;
		}
		if (!userAgentString.equals(other.userAgentString))
		{
			return false;
		}
		return icon.equals(other.icon);
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("ReadableRobot [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", family=");
		builder.append(family);
		builder.append(", familyName=");
		builder.append(familyName);
		builder.append(", infoUrl=");
		builder.append(infoUrl);
		builder.append(", producer=");
		builder.append(producer);
		builder.append(", producerUrl=");
		builder.append(producerUrl);
		builder.append(", userAgentString=");
		builder.append(userAgentString);
		builder.append(", icon=");
		builder.append(icon);
		builder.append("]");
		return builder.toString();
	}


	public static final class Builder
	{

		private static final String EMPTY = "";

		@jakarta.validation.constraints.NotNull
		private UserAgentFamily family = UserAgentFamily.UNKNOWN;

		@jakarta.validation.constraints.NotNull
		private String familyName = EMPTY;

		@jakarta.validation.constraints.NotNull
		private String icon = EMPTY;

		private int id = Integer.MIN_VALUE;

		@jakarta.validation.constraints.NotNull
		private String infoUrl = EMPTY;

		@jakarta.validation.constraints.NotNull
		private String name = EMPTY;

		@jakarta.validation.constraints.NotNull
		private String producer = EMPTY;

		@jakarta.validation.constraints.NotNull
		private String producerUrl = EMPTY;

		@jakarta.validation.constraints.NotNull
		private String userAgentString = EMPTY;

		public Builder()
		{
			// default constructor
		}

		public Builder(@jakarta.validation.constraints.NotNull Robot robot)
		{
			Check.notNull(robot, "robot");
			id = Check.notNegative(robot.getId(), "robot.getId()");
			name = Check.notNull(robot.getName(), "robot.getName()");
			family = Check.notNull(robot.getFamily(), "robot.getFamily()");
			familyName = Check.notNull(robot.getFamilyName(), "robot.getFamilyName()");
			infoUrl = Check.notNull(robot.getInfoUrl(), "robot.getInfoUrl()");
			producer = Check.notNull(robot.getProducer(), "robot.getProducer()");
			producerUrl = Check.notNull(robot.getProducerUrl(), "robot.getProducerUrl()");
			userAgentString = Check.notNull(robot.getUserAgentString(), "robot.getUserAgentString()");
			icon = Check.notNull(robot.getIcon(), "robot.getIcon()");
		}

		@jakarta.validation.constraints.NotNull
		public Robot build()
		{
			return new Robot(id, name, family, familyName, infoUrl, producer, producerUrl, userAgentString, icon);
		}

		@jakarta.validation.constraints.NotNull
		public Builder setFamilyName(@jakarta.validation.constraints.NotNull String familyName)
		{
			this.familyName = Check.notNull(familyName, "familyName");
			family = UserAgentFamily.evaluate(familyName);
			return this;
		}

		@jakarta.validation.constraints.NotNull
		public Builder setIcon(@jakarta.validation.constraints.NotNull String icon)
		{
			this.icon = Check.notNull(icon, "icon");
			return this;
		}

		@jakarta.validation.constraints.NotNull
		public Builder setId(int id)
		{
			this.id = Check.notNegative(id, "id");
			return this;
		}

		@jakarta.validation.constraints.NotNull
		public Builder setId(@jakarta.validation.constraints.NotNull String id)
		{
			this.id = Integer.parseInt(Check.notEmpty(id, "id"));
			return this;
		}

		@jakarta.validation.constraints.NotNull
		public Builder setInfoUrl(@jakarta.validation.constraints.NotNull String infoUrl)
		{
			this.infoUrl = Check.notNull(infoUrl, "infoUrl");
			return this;
		}

		@jakarta.validation.constraints.NotNull
		public Builder setName(@jakarta.validation.constraints.NotNull String name)
		{
			this.name = Check.notNull(name, "name");
			return this;
		}

		@jakarta.validation.constraints.NotNull
		public Builder setProducer(@jakarta.validation.constraints.NotNull String producer)
		{
			this.producer = Check.notNull(producer, "producer");
			return this;
		}

		@jakarta.validation.constraints.NotNull
		public Builder setProducerUrl(@jakarta.validation.constraints.NotNull String producerUrl)
		{
			this.producerUrl = Check.notNull(producerUrl, "producerUrl");
			return this;
		}

		@jakarta.validation.constraints.NotNull
		public Builder setUserAgentString(@jakarta.validation.constraints.NotNull String userAgentString)
		{
			this.userAgentString = Check.notNull(userAgentString, "userAgentString");
			return this;
		}

	}

}
