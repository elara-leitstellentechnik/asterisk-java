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
package org.asteriskjava.fastagi;

import org.asteriskjava.fastagi.command.AgiCommand;
import org.asteriskjava.fastagi.internal.AgiConnectionHandler;
import org.asteriskjava.fastagi.reply.AgiReply;

/**
 * AgiOperations provides some convinience methods that wrap the various
 * {@link AgiCommand AgiCommands}.
 *
 * @since 0.3
 * @author srt
 * @version $Id$
 */
public class AgiOperations implements AgiChannel
{
    private final AgiChannel channel;

    /**
     * Creates a new instance that operates on the channel attached to the
     * current thread.
     */
    public AgiOperations()
    {
        this.channel = null;
    }

    /**
     * Creates a new instance that operates on the given channel.
     *
     * @param channel the channel to operate on.
     */
    public AgiOperations(AgiChannel channel)
    {
        this.channel = channel;
    }

    /**
     * Returns the channel to operate on.
     *
     * @return the channel to operate on.
     * @throws IllegalStateException if no {@link AgiChannel} is bound to the
     *             current channel and no channel has been passed to the
     *             constructor.
     */
    protected AgiChannel getChannel()
    {
        AgiChannel threadBoundChannel;

        if (channel != null)
        {
            return channel;
        }

        threadBoundChannel = AgiConnectionHandler.getChannel();
        if (threadBoundChannel == null)
        {
            throw new IllegalStateException("Trying to send command from an invalid thread");
        }

        return threadBoundChannel;
    }

    /* The following methods simply delegate to #getChannel() */

    @Override
    public String getName()
    {
        return getChannel().getName();
    }

    @Override
    public String getUniqueId()
    {
        return getChannel().getUniqueId();
    }

    @Override
    public AgiReply getLastReply()
    {
        return getChannel().getLastReply();
    }

    @Override
    public AgiReply sendCommand(AgiCommand command) throws AgiException
    {
        return getChannel().sendCommand(command);
    }

    @Override
    public void answer() throws AgiException
    {
        getChannel().answer();
    }

    @Override
    public void hangup() throws AgiException
    {
        getChannel().hangup();
    }

    @Override
    public void setAutoHangup(int time) throws AgiException
    {
        getChannel().setAutoHangup(time);
    }

    @Override
    public void setCallerId(String callerId) throws AgiException
    {
        getChannel().setCallerId(callerId);
    }

    @Override
    public void playMusicOnHold() throws AgiException
    {
        getChannel().playMusicOnHold();
    }

    @Override
    public void playMusicOnHold(String musicOnHoldClass) throws AgiException
    {
        getChannel().playMusicOnHold(musicOnHoldClass);
    }

    @Override
    public void stopMusicOnHold() throws AgiException
    {
        getChannel().stopMusicOnHold();
    }

    @Override
    public int getChannelStatus() throws AgiException
    {
        return getChannel().getChannelStatus();
    }

    @Override
    public String getData(String file) throws AgiException
    {
        return getChannel().getData(file);
    }

    @Override
    public String getData(String file, long timeout) throws AgiException
    {
        return getChannel().getData(file, timeout);
    }

    @Override
    public String getData(String file, long timeout, int maxDigits) throws AgiException
    {
        return getChannel().getData(file, timeout, maxDigits);
    }

    @Override
    public char getOption(String file, String escapeDigits) throws AgiException
    {
        return getChannel().getOption(file, escapeDigits);
    }

    @Override
    public char getOption(String file, String escapeDigits, long timeout) throws AgiException
    {
        return getChannel().getOption(file, escapeDigits, timeout);
    }

    @Override
    public int exec(String application) throws AgiException
    {
        return getChannel().exec(application);
    }

    @Override
    public int exec(String application, String... options) throws AgiException
    {
        return getChannel().exec(application, options);
    }

    @Override
    public void setContext(String context) throws AgiException
    {
        getChannel().setContext(context);
    }

    @Override
    public void setExtension(String extension) throws AgiException
    {
        getChannel().setExtension(extension);
    }

    @Override
    public void setPriority(String priority) throws AgiException
    {
        getChannel().setPriority(priority);
    }

    @Override
    public void streamFile(String file) throws AgiException
    {
        getChannel().streamFile(file);
    }

    @Override
    public char streamFile(String file, String escapeDigits) throws AgiException
    {
        return getChannel().streamFile(file, escapeDigits);
    }

    @Override
    public char streamFile(String file, String escapeDigits, int offset) throws AgiException
    {
        return getChannel().streamFile(file, escapeDigits, offset);
    }

    @Override
    public void sayDigits(String digits) throws AgiException
    {
        getChannel().sayDigits(digits);
    }

    @Override
    public char sayDigits(String digits, String escapeDigits) throws AgiException
    {
        return getChannel().sayDigits(digits, escapeDigits);
    }

    @Override
    public void sayNumber(String number) throws AgiException
    {
        getChannel().sayNumber(number);
    }

    @Override
    public char sayNumber(String number, String escapeDigits) throws AgiException
    {
        return getChannel().sayNumber(number, escapeDigits);
    }

    @Override
    public void sayPhonetic(String text) throws AgiException
    {
        getChannel().sayPhonetic(text);
    }

    @Override
    public char sayPhonetic(String text, String escapeDigits) throws AgiException
    {
        return getChannel().sayPhonetic(text, escapeDigits);
    }

    @Override
    public void sayAlpha(String text) throws AgiException
    {
        getChannel().sayAlpha(text);
    }

    @Override
    public char sayAlpha(String text, String escapeDigits) throws AgiException
    {
        return getChannel().sayAlpha(text, escapeDigits);
    }

    @Override
    public void sayTime(long time) throws AgiException
    {
        getChannel().sayTime(time);
    }

    @Override
    public char sayTime(long time, String escapeDigits) throws AgiException
    {
        return getChannel().sayTime(time, escapeDigits);
    }

    @Override
    public String getVariable(String name) throws AgiException
    {
        return getChannel().getVariable(name);
    }

    @Override
    public void setVariable(String name, String value) throws AgiException
    {
        getChannel().setVariable(name, value);
    }

    @Override
    public char waitForDigit(int timeout) throws AgiException
    {
        return getChannel().waitForDigit(timeout);
    }

    @Override
    public String getFullVariable(String name) throws AgiException
    {
        return getChannel().getFullVariable(name);
    }

    @Override
    public String getFullVariable(String name, String channel) throws AgiException
    {
        return getChannel().getFullVariable(name, channel);
    }

    @Override
    public void sayDateTime(long time) throws AgiException
    {
        getChannel().sayDateTime(time);
    }

    @Override
    public char sayDateTime(long time, String escapeDigits) throws AgiException
    {
        return getChannel().sayDateTime(time, escapeDigits);
    }

    @Override
    public char sayDateTime(long time, String escapeDigits, String format) throws AgiException
    {
        return getChannel().sayDateTime(time, escapeDigits, format);
    }

    @Override
    public char sayDateTime(long time, String escapeDigits, String format, String timezone) throws AgiException
    {
        return getChannel().sayDateTime(time, escapeDigits, format, timezone);
    }

    @Override
    public String databaseGet(String family, String key) throws AgiException
    {
        return getChannel().databaseGet(family, key);
    }

    @Override
    public void databasePut(String family, String key, String value) throws AgiException
    {
        getChannel().databasePut(family, key, value);
    }

    @Override
    public void databaseDel(String family, String key) throws AgiException
    {
        getChannel().databaseDel(family, key);
    }

    @Override
    public void databaseDelTree(String family) throws AgiException
    {
        getChannel().databaseDelTree(family);
    }

    @Override
    public void databaseDelTree(String family, String keytree) throws AgiException
    {
        getChannel().databaseDelTree(family, keytree);
    }

    @Override
    public void verbose(String message, int level) throws AgiException
    {
        getChannel().verbose(message, level);
    }

    @Override
    public char recordFile(String file, String format, String escapeDigits, int timeout) throws AgiException
    {
        return getChannel().recordFile(file, format, escapeDigits, timeout);
    }

    @Override
    public char recordFile(String file, String format, String escapeDigits, int timeout, int offset, boolean beep,
            int maxSilence) throws AgiException
    {
        return getChannel().recordFile(file, format, escapeDigits, timeout, offset, beep, maxSilence);
    }

    @Override
    public void controlStreamFile(String file) throws AgiException
    {
        getChannel().controlStreamFile(file);
    }

    @Override
    public char controlStreamFile(String file, String escapeDigits) throws AgiException
    {
        return getChannel().controlStreamFile(file, escapeDigits);
    }

    @Override
    public char controlStreamFile(String file, String escapeDigits, int offset) throws AgiException
    {
        return getChannel().controlStreamFile(file, escapeDigits, offset);
    }

    @Override
    public char controlStreamFile(String file, String escapeDigits, int offset, String forwardDigit, String rewindDigit,
            String pauseDigit) throws AgiException
    {
        return getChannel().controlStreamFile(file, escapeDigits, offset, forwardDigit, rewindDigit, pauseDigit);
    }

    @Override
    public void speechCreate() throws AgiException
    {
        getChannel().speechCreate();
    }

    @Override
    public void speechCreate(String engine) throws AgiException
    {
        getChannel().speechCreate(engine);
    }

    @Override
    public void speechSet(String name, String value) throws AgiException
    {
        getChannel().speechSet(name, value);
    }

    @Override
    public void speechDestroy() throws AgiException
    {
        getChannel().speechDestroy();
    }

    @Override
    public void speechLoadGrammar(String name, String path) throws AgiException
    {
        getChannel().speechLoadGrammar(name, path);
    }

    @Override
    public void speechUnloadGrammar(String name) throws AgiException
    {
        getChannel().speechUnloadGrammar(name);
    }

    @Override
    public void speechActivateGrammar(String name) throws AgiException
    {
        getChannel().speechActivateGrammar(name);
    }

    @Override
    public void speechDeactivateGrammar(String name) throws AgiException
    {
        getChannel().speechDeactivateGrammar(name);
    }

    @Override
    public SpeechRecognitionResult speechRecognize(String prompt, int timeout) throws AgiException
    {
        return getChannel().speechRecognize(prompt, timeout);
    }

    @Override
    public SpeechRecognitionResult speechRecognize(String prompt, int timeout, int offset) throws AgiException
    {
        return getChannel().speechRecognize(prompt, timeout, offset);
    }

    @Override
    public void continueAt(String context, String extension, String priority) throws AgiException
    {
        getChannel().continueAt(context, extension, priority);
    }

    @Override
    public void gosub(String context, String extension, String priority) throws AgiException
    {
        getChannel().gosub(context, extension, priority);
    }

    @Override
    public void gosub(String context, String extension, String priority, String... arguments) throws AgiException
    {
        getChannel().gosub(context, extension, priority, arguments);
    }

    @Override
    public void confbridge(String room, String profile) throws AgiException
    {
        getChannel().confbridge(room, profile);

    }

    @Override
    public void meetme(String room, String options) throws AgiException
    {
        getChannel().meetme(room, options);

    }

    @Override
    public void dial(String target, int timeout, String options) throws AgiException
    {
        getChannel().dial(target, timeout, options);

    }

    @Override
    public void bridge(String channelName, String options) throws AgiException
    {
        getChannel().bridge(channelName, options);

    }

    @Override
    public void queue(String queue) throws AgiException
    {
        getChannel().queue(queue);

    }
}
