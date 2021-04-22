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
package org.asteriskjava.live;

import java.util.HashMap;
import java.util.Map;

/**
 * Asterisk hangup cause.<p>
 * Definitions from <code>/include/asterisk/causes.h</code>.
 *
 * @author srt
 * @version $Id$
 */
public enum HangupCause
{
    AST_CAUSE_1_UNALLOCATED,
    AST_CAUSE_2_NO_ROUTE_TRANSIT_NET,
    AST_CAUSE_3_NO_ROUTE_DESTINATION,
    AST_CAUSE_5_MISDIALLED_TRUNK_PREFIX,
    AST_CAUSE_6_CHANNEL_UNACCEPTABLE,
    AST_CAUSE_7_CALL_AWARDED_DELIVERED,
    AST_CAUSE_14_NUMBER_PORTED_NOT_HERE,
    AST_CAUSE_16_NORMAL_CLEARING,
    AST_CAUSE_17_USER_BUSY,
    AST_CAUSE_18_NO_USER_RESPONSE,
    AST_CAUSE_19_NO_ANSWER,
    AST_CAUSE_20_SUBSCRIBER_ABSENT,
    AST_CAUSE_21_CALL_REJECTED,
    AST_CAUSE_22_NUMBER_CHANGED,
    AST_CAUSE_23_REDIRECTED_TO_NEW_DESTINATION,
    AST_CAUSE_26_ANSWERED_ELSEWHERE,
    AST_CAUSE_27_DESTINATION_OUT_OF_ORDER,
    AST_CAUSE_28_INVALID_NUMBER_FORMAT,
    AST_CAUSE_29_FACILITY_REJECTED,
    AST_CAUSE_30_RESPONSE_TO_STATUS_ENQUIRY,
    AST_CAUSE_31_NORMAL_UNSPECIFIED,
    AST_CAUSE_34_NORMAL_CIRCUIT_CONGESTION,
    AST_CAUSE_38_NETWORK_OUT_OF_ORDER,
    AST_CAUSE_41_NORMAL_TEMPORARY_FAILURE,
    AST_CAUSE_42_SWITCH_CONGESTION,
    AST_CAUSE_43_ACCESS_INFO_DISCARDED,
    AST_CAUSE_44_REQUESTED_CHAN_UNAVAIL,
    AST_CAUSE_45_PRE_EMPTED,
    AST_CAUSE_50_FACILITY_NOT_SUBSCRIBED,
    AST_CAUSE_52_OUTGOING_CALL_BARRED,
    AST_CAUSE_54_INCOMING_CALL_BARRED,
    AST_CAUSE_57_BEARERCAPABILITY_NOTAUTH,
    AST_CAUSE_58_BEARERCAPABILITY_NOTAVAIL,
    AST_CAUSE_65_BEARERCAPABILITY_NOTIMPL,
    AST_CAUSE_66_CHAN_NOT_IMPLEMENTED,
    AST_CAUSE_69_FACILITY_NOT_IMPLEMENTED,
    AST_CAUSE_81_INVALID_CALL_REFERENCE,
    AST_CAUSE_88_INCOMPATIBLE_DESTINATION,
    AST_CAUSE_95_INVALID_MSG_UNSPECIFIED,
    AST_CAUSE_96_MANDATORY_IE_MISSING,
    AST_CAUSE_97_MESSAGE_TYPE_NONEXIST,
    AST_CAUSE_98_WRONG_MESSAGE,
    AST_CAUSE_99_IE_NONEXIST,
    AST_CAUSE_100_INVALID_IE_CONTENTS,
    AST_CAUSE_101_WRONG_CALL_STATE,
    AST_CAUSE_102_RECOVERY_ON_TIMER_EXPIRE,
    AST_CAUSE_103_MANDATORY_IE_LENGTH_ERROR,
    AST_CAUSE_111_PROTOCOL_ERROR,
    AST_CAUSE_127_INTERWORKING,
	;

    /* Special Asterisk aliases */
    public static final HangupCause AST_CAUSE_17_BUSY = AST_CAUSE_17_USER_BUSY;
    public static final HangupCause AST_CAUSE_38_FAILURE = AST_CAUSE_38_NETWORK_OUT_OF_ORDER;
    public static final HangupCause AST_CAUSE_16_NORMAL = AST_CAUSE_16_NORMAL_CLEARING;
    public static final HangupCause AST_CAUSE_34_CONGESTION = AST_CAUSE_34_NORMAL_CIRCUIT_CONGESTION;
    public static final HangupCause AST_CAUSE_3_UNREGISTERED = AST_CAUSE_3_NO_ROUTE_DESTINATION;
    public static final HangupCause AST_CAUSE_66_NO_SUCH_DRIVER = AST_CAUSE_66_CHAN_NOT_IMPLEMENTED;

    HangupCause()
    {
	    String code = name().substring(10, name().indexOf('_',11));
	    this.code = Integer.parseInt(code);
    }

	/**
     * Returns the numeric cause code.<p>
     * Using this method in client code is discouraged.
     *
     * @return the numeric cause code.
     */
    public int getCode()
    {
        return code;
    }

    /**
     * Returns the HangupCode by its numeric cause code.<p>
     * Using this method in client code is discouraged.
     *
     * @param code the numeric cause code.
     * @return the corresponding HangupCode enum or
     *         <code>null</code> if there is no such HangupCause.
     */
    public static synchronized HangupCause getByCode(int code)
    {
        if (causes == null)
        {
            Map<Integer, HangupCause> causes = new HashMap<>();
            for (HangupCause cause : values())
            {
                causes.put(cause.code, cause);
            }
            assert causes.size() == values().length;
            HangupCause.causes = causes;
        }

        return causes.get(code);
    }

    @Override
    public String toString()
    {
	    assert name().startsWith("AST_CAUSE_");
	    int i = name().indexOf('_', 11);
	    assert i > 10;
	    return name().substring(i + 1);
    }

    private final int code;
    private static Map<Integer, HangupCause> causes;
}
