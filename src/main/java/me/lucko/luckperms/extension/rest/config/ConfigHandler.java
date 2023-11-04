/*
 * This file is part of LuckPerms, licensed under the MIT License.
 *
 *  Copyright (c) lucko (Luck) <luck@lucko.me>
 *  Copyright (c) contributors
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */

package me.lucko.luckperms.extension.rest.config;

public class ConfigHandler {

    private final ConfigBuilder config;

    public ConfigHandler() {
        config = new ConfigBuilder("rest-config");
        init();
    }

    private void init() {
        config.add("LUCKPERMS_REST_HTTP_HOST", "127.0.0.1");
        config.add("LUCKPERMS_REST_HTTP_PORT", 8080);
        config.add("LUCKPERMS_REST_AUTH", false);
        config.add("LUCKPERMS_REST_AUTH_KEYS", "myverysecureapikey,anotherverysecurekey");
        config.add("LUCKPERMS_REST_CACHE_USERS", true);
        config.add("LUCKPERMS_REST_CACHE_GROUPS", true);
        config.add("LUCKPERMS_REST_CACHE_TRACKS", true);

        config.save();
    }

    public String getHost() {
        return config.getString("LUCKPERMS_REST_HTTP_HOST");
    }

    public int getPort() {
        return config.getInt("LUCKPERMS_REST_HTTP_PORT");
    }

    public boolean getAuth() {
        return config.getBoolean("LUCKPERMS_REST_AUTH");
    }

    public String getAuthKeys() {
        return config.getString("LUCKPERMS_REST_AUTH_KEYS");
    }

    public boolean getCacheUsers() {
        return config.getBoolean("LUCKPERMS_REST_CACHE_USERS");
    }

    public boolean getCacheGroups() {
        return config.getBoolean("LUCKPERMS_REST_CACHE_GROUPS");
    }

    public boolean getCacheTracks() {
        return config.getBoolean("LUCKPERMS_REST_CACHE_TRACKS");
    }

}
