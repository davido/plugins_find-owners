// Copyright (C) 2017 The Android Open Source Project
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.googlesource.gerrit.plugins.findowners;

import com.google.common.collect.Ordering;
import java.io.File;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/** Utility classes and functions. */
class Util {
  /** Strips REST magic prefix line. */
  static String stripMagicPrefix(String data) {
    final String magic = ")]}'\n";
    return data.startsWith(magic) ? data.substring(magic.length()) : data;
  }

  static String getDirName(String path) {
    return new File(path).getParent();
  }

  static String normalizedFilePath(String path) {
    return path.startsWith("./") ? path : ("./" + path);
  }

  static String normalizedDirPath(String path) {
    return new File(normalizedFilePath(path)).getParent();
  }

  static boolean parseBoolean(String s) {
    return (s != null) && (s.equals("1") || s.equalsIgnoreCase("yes") || Boolean.parseBoolean(s));
  }

  static SortedMap<String, String> makeSortedMap(Map<String, Set<String>> map) {
    SortedMap<String, String> result = new TreeMap<>();
    for (String key : Ordering.natural().sortedCopy(map.keySet())) {
      result.put(key, String.join(" ", Ordering.natural().sortedCopy(map.get(key))));
    }
    return result;
  }

  static void addToMap(Map<String, Set<String>> map, String key, String value) {
    if (map.get(key) == null) {
      map.put(key, new HashSet<>());
    }
    map.get(key).add(value);
  }
}
