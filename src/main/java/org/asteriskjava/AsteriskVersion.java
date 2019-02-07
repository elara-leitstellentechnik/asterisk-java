/*
 *  Copyright 2004-2006 Stefan Reuter
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package org.asteriskjava;

import java.io.Serializable;
import java.util.regex.Pattern;

/**
 * Represents the version of an Asterisk server.
 *
 * @author srt
 * @version $Id$
 * @since 0.2
 */
public class AsteriskVersion implements Comparable<AsteriskVersion>, Serializable
{
    private static final String VERSION_PATTERN_16 = "^\\s*Asterisk (GIT-)?16[-. ].*";
    private static final String VERSION_PATTERN_15 = "^\\s*Asterisk (GIT-)?15[-. ].*";
    private static final String VERSION_PATTERN_14 = "^\\s*Asterisk (GIT-)?14[-. ].*";
    private static final String VERSION_PATTERN_13 = "^\\s*Asterisk ((SVN-branch|GIT)-)?13[-. ].*";
    private static final String VERSION_PATTERN_12 = "^\\s*Asterisk ((SVN-branch|GIT)-)?12[-. ].*";
    private static final String VERSION_PATTERN_11 = "^\\s*Asterisk ((SVN-branch|GIT)-)?11[-. ].*";
    private static final String VERSION_PATTERN_10 = "^\\s*Asterisk ((SVN-branch|GIT)-)?10[-. ].*";
    private static final String VERSION_PATTERN_1_8 = "^\\s*Asterisk ((SVN-branch|GIT)-)?1\\.8[-. ].*";
    private static final String VERSION_PATTERN_1_6 = "^\\s*Asterisk ((SVN-branch|GIT)-)?1\\.6[-. ].*";
    private static final String VERSION_PATTERN_1_4 = "^\\s*Asterisk ((SVN-branch|GIT)-)?1\\.4[-. ].*";
    private final int version;
    private final String versionString;
    private final Pattern patterns[];

    private static final String VERSION_PATTERN_CERTIFIED_13 = "^\\s*Asterisk certified/((SVN-branch|GIT)-)?13[-. ].*";
    private static final String VERSION_PATTERN_CERTIFIED_11 = "^\\s*Asterisk certified/((SVN-branch|GIT)-)?11[-. ].*";

    /**
     * Represents the Asterisk 1.0 series.
     */
    public static final AsteriskVersion ASTERISK_1_0 = new AsteriskVersion(100, "Asterisk 1.0");

    /**
     * Represents the Asterisk 1.2 series.
     */
    public static final AsteriskVersion ASTERISK_1_2 = new AsteriskVersion(120, "Asterisk 1.2");

    /**
     * Represents the Asterisk 1.4 series.
     *
     * @since 0.3
     */
    public static final AsteriskVersion ASTERISK_1_4 = new AsteriskVersion(140, "Asterisk 1.4", VERSION_PATTERN_1_4);

    /**
     * Represents the Asterisk 1.6 series.
     *
     * @since 1.0.0
     */
    public static final AsteriskVersion ASTERISK_1_6 = new AsteriskVersion(160, "Asterisk 1.6", VERSION_PATTERN_1_6);

    /**
     * Represents the Asterisk 1.8 series.
     *
     * @since 1.0.0
     */
    public static final AsteriskVersion ASTERISK_1_8 = new AsteriskVersion(180, "Asterisk 1.8", VERSION_PATTERN_1_8);

    /**
     * Represents the Asterisk 10 series.
     *
     * @since 1.0.0
     */
    public static final AsteriskVersion ASTERISK_10 = new AsteriskVersion(1000, "Asterisk 10", VERSION_PATTERN_10);

    /**
     * Represents the Asterisk 11 series.
     *
     * @since 1.0.0
     */
    public static final AsteriskVersion ASTERISK_11 = new AsteriskVersion(1100, "Asterisk 11", VERSION_PATTERN_11, VERSION_PATTERN_CERTIFIED_11);

    /**
     * Represents the Asterisk 12 series.
     *
     * @since 1.0.0
     */
    public static final AsteriskVersion ASTERISK_12 = new AsteriskVersion(1200, "Asterisk 12", VERSION_PATTERN_12);

    /**
     * Represents the Asterisk 13 series.
     *
     * @since 1.0.0
     */
    public static final AsteriskVersion ASTERISK_13 = new AsteriskVersion(1300, "Asterisk 13", VERSION_PATTERN_13, VERSION_PATTERN_CERTIFIED_13);

    /**
     * Represents the Asterisk 14 series.
     *
     * @since 1.1.0
     */
    public static final AsteriskVersion ASTERISK_14 = new AsteriskVersion(1400, "Asterisk 14", VERSION_PATTERN_14);

	/**
	 * Represents the Asterisk 15 series.
	 *
	 * @since 2.0.3
	 */
	public static final AsteriskVersion ASTERISK_15 = new AsteriskVersion(1500, "Asterisk 15", VERSION_PATTERN_15);

	/**
	 * Represents the Asterisk 16 series.
	 *
	 * @since 2.1.0
	 */
	public static final AsteriskVersion ASTERISK_16 = new AsteriskVersion(1600, "Asterisk 16", VERSION_PATTERN_16);

    public static final AsteriskVersion UNKNOWN_VERSION = new AsteriskVersion(Integer.MAX_VALUE, "Asterisk ?");

	private static final AsteriskVersion knownVersions[] = new AsteriskVersion[]{ASTERISK_16, ASTERISK_15, ASTERISK_14, ASTERISK_13,
            ASTERISK_12, ASTERISK_11, ASTERISK_10, ASTERISK_1_8, ASTERISK_1_6, ASTERISK_1_4};

    /**
     * Serial version identifier.
     */
    private static final long serialVersionUID = 1L;

    private AsteriskVersion(int version, String versionString, String... patterns)
    {
        this.version = version;
        this.versionString = versionString;

        this.patterns = new Pattern[patterns.length];
        int i = 0;
        for (String pattern : patterns)
        {
            this.patterns[i++] = Pattern.compile(pattern);
        }
    }

    /**
     * Returns <code>true</code> if this version is equal to or higher than the
     * given version.
     *
     * @param o the version to compare to
     * @return <code>true</code> if this version is equal to or higher than the
     *         given version, <code>false</code> otherwise.
     */
    public boolean isAtLeast(AsteriskVersion o)
    {
        return version >= o.version;
    }

    public int compareTo(AsteriskVersion o)
    {
        int otherVersion;

        otherVersion = o.version;
        if (version < otherVersion)
        {
            return -1;
        }
        else if (version > otherVersion)
        {
            return 1;
        }
        else
        {
            return 0;
        }
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }

        AsteriskVersion that = (AsteriskVersion) o;

        return version == that.version;
    }

    @Override
    public int hashCode()
    {
        return version;
    }

    @Override
    public String toString()
    {
        return versionString;
    }

    public static AsteriskVersion getDetermineVersionFromString(String coreLine)
    {
        for (AsteriskVersion version : knownVersions)
        {
            for (Pattern pattern : version.patterns)
            {
                if (pattern.matcher(coreLine).matches())
                {
                    return version;
                }
            }
        }

        return UNKNOWN_VERSION;
    }
}
