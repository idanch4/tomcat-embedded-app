/*
 * Copyright 2017-2018 Blue Lotus Software, LLC.
 * Copyright 2017-2018 John Yeary <jyeary@bluelotussoftware.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.idanch;

import com.idanch.data.DbBootstrap;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.WebResourceSet;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;

public class Main {
    public static final Logger log = LoggerFactory.getLogger(Main.class);

    /**
     * Main method.
     *
     * @throws LifecycleException If a life cycle exception occurs.
     * @throws SQLException If the embedded h2 db throws an exception
     */
    public static void main(String[] args)
            throws LifecycleException, SQLException {

        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8082);
        tomcat.getConnector();

        Path webappFile = Paths.get("src", "main", "webapp");
        StandardContext ctx = (StandardContext) tomcat.addWebapp("",
                webappFile.toAbsolutePath().toString());

        WebResourceRoot webResourceRoot = new StandardRoot(ctx);
        WebResourceSet webResourceSet = new DirResourceSet(webResourceRoot,
                "/WEB-INF/classes",
                new File("target/classes").getAbsolutePath(),
                "/");
        webResourceRoot.addPreResources(webResourceSet);
        ctx.setResources(webResourceRoot);

        // add users manually
        tomcat.addUser("idan", "1234");
        tomcat.addRole("idan", "user");
        tomcat.addRole("idan", "admin");

        tomcat.addUser("test", "1234");
        tomcat.addRole("test", "user");
        tomcat.addRole("test", "1234");

        // H2 Db Bootstrap
        DbBootstrap.initialize();

        tomcat.start();
        tomcat.getServer().await();
    }
}