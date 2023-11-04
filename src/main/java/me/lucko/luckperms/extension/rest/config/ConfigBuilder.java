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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonNull;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConfigBuilder {

    private final File file;
    private final Gson gson;
    private final ExecutorService pool;
    private JSONObject json;

    public ConfigBuilder(String name) {
        this.file = new File("plugins/luckperms/", name.toLowerCase() + ".json");
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.pool = Executors.newFixedThreadPool(2);
        this.initFile();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void initFile() {
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();

        if (!file.exists()) {
            try (final PrintWriter writer = new PrintWriter(file)) {
                writer.print(gson.toJson(json = new JSONObject()));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            try {
                json = (JSONObject) new JSONParser().parse(new FileReader(file));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public <V> ConfigBuilder add(String key, V value) {
        if (json.get(key) == null || json.get(key) instanceof JsonNull) {
            return set(key, value);
        }

        return this;
    }

    @SuppressWarnings("unchecked")
    public <V> ConfigBuilder set(String key, V value) {
        if (value instanceof String) {
            json.put(key, value);
            return this;
        }

        if (value instanceof Character) {
            json.put(key, value);
            return this;
        }

        if (value instanceof Number) {
            json.put(key, value);
            return this;
        }

        if (value instanceof Boolean) {
            json.put(key, value);
            return this;
        }

        json.put(key, value);
        return this;
    }

    public void save() {
        pool.execute(() -> {
            try (final PrintWriter writer = new PrintWriter(file)) {
                writer.print(gson.toJson(json));
                writer.flush();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    public String getString(String key) {
        return json.get(key).toString();
    }

    public int getInt(String key) {
        return Integer.parseInt(json.get(key).toString());
    }

    public boolean getBoolean(String key) {
        return Boolean.parseBoolean(json.get(key).toString());
    }

}
