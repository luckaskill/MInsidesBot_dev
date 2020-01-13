package com.las.bots.minsides.server.tools;

import com.las.bots.minsides.server.notes.config.HibSessionFactory;
import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
class DBUpdater {
    private static final String PATCH_PREFIX = "patch_";
    private static final String PATCHES_FOLDER = "patches";
    private static final String SQL_GET_DB_VERSION = "SELECT value FROM global_settings WHERE property='version'";
    private static final String SQL_UPDATE_DB_VERSION = "UPDATE global_settings SET value=? WHERE property='version';";

    public DBUpdater() throws IOException {
//        System.out.println("DB Updating");
//        int dbVersion = getDBVersion();
//        List<Patch> patchesToApply = findPatchesAfterVersion(dbVersion);
//        if (!patchesToApply.isEmpty()) {
//            applyPatches(patchesToApply);
//            int newVersion = patchesToApply.get(patchesToApply.size() - 1).number;
//            updateDBVersion(newVersion);
//        }
//        System.out.println("DB is up to date");
    }

    private void updateDBVersion(int newVersion) {
        @Cleanup
        Session session = HibSessionFactory.open();
        Transaction transaction = session.beginTransaction();
        session.createSQLQuery(SQL_UPDATE_DB_VERSION)
                .setParameter(1, newVersion)
                .executeUpdate();
        transaction.commit();
    }

    private void applyPatches(List<Patch> patches) {
        @Cleanup final Session session = HibSessionFactory.open();
        patches.forEach((patch -> {
            try {
                Transaction transaction = session.beginTransaction();
                System.out.println("Applying " + patch.number + " patch...");
                NativeQuery sqlQuery = session.createSQLQuery(patch.getQuery());
                sqlQuery.executeUpdate();
                transaction.commit();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }));
    }

    private List<Patch> findPatchesAfterVersion(int version) throws IOException {
        List<Patch> result = new ArrayList<>();
        URL patches = getClass().getClassLoader().getResource(PATCHES_FOLDER);
        String path = patches.getPath();
        File[] files = new File(path).listFiles();
        for (File file : files) {
            Patch patch = new Patch(file);
            if (patch.number > version) {
                result.add(patch);
            }
        }
        return result;
    }

    private int getDBVersion() {
        @Cleanup
        Session session = HibSessionFactory.open();
        NativeQuery sqlQuery = session.createSQLQuery(SQL_GET_DB_VERSION);
        int version = Integer.parseInt(sqlQuery.uniqueResult().toString());
        return version;
    }

    private static class Patch {
        private File file;
        private int number;
        private String query;

        private Patch(File file) {
            this.file = file;
            number = defineNumber(file);
        }

        private String getQuery() throws IOException {
            if (query == null) {
                query = readQuery(file);
            }
            return query;
        }

        private static String readQuery(File file) throws IOException {
            StringBuilder builder = new StringBuilder();
            Path path = Paths.get(file.getCanonicalPath());
            Files.lines(path)
                    .forEach(builder::append);
            return builder.toString();
        }

        private static int defineNumber(File file) {
            String name = file.getName();
            name = name.replace(PATCH_PREFIX, "");
            String substring = name.substring(0, name.indexOf("."));
            int number = Integer.parseInt(substring);
            return number;
        }
    }
}
