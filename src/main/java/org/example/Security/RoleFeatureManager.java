package org.example.Security;

import java.util.*;

public class RoleFeatureManager {

    // Map uses lowercase role names as keys
    private static final Map<String, Set<Feature>> roleFeatures = new HashMap<>();

    static {

        // ADMIN permissions: Full access
        roleFeatures.put("admin", Set.of(
                Feature.CREATE_USER,
                Feature.UPDATE_USER,
                Feature.DELETE_USER,
                Feature.VIEW_USERS,
                Feature.CREATE_BILLING,
                Feature.VIEW_BILLING,
                Feature.DELETE_BILLING,
                Feature.UPDATE_PASSWORD,
                Feature.CREATE_ROOM,
                Feature.UPDATE_ROOM,
                Feature.DELETE_ROOM,
                Feature.CREATE_ROOM_TYPE,
                Feature.VIEW_ROOMS,
                Feature.CREATE_RESERVATION,
                Feature.UPDATE_RESERVATION,
                Feature.DELETE_RESERVATION,
                Feature.VIEW_RESERVATIONS,
                Feature.VIEW_ACTIVITY_LOGS,

                Feature.ACCESS_USERSPAGE,
                Feature.ACCESS_ROOMPAGE,
                Feature.ACCESS_RESERVATIONPAGE,
                Feature.ACCESS_GUEST_PAGE
        ));

        // RECEPTIONIST permissions: Limited access
        roleFeatures.put("receptionist", Set.of(
                Feature.VIEW_USERS,
                Feature.CREATE_BILLING,
                Feature.VIEW_BILLING,
                Feature.UPDATE_PASSWORD,
                Feature.CREATE_RESERVATION,
                Feature.VIEW_RESERVATIONS
        ));

        // MANAGER permissions: View-only for monitoring
        roleFeatures.put("manager", Set.of(
                Feature.VIEW_USERS,
                Feature.VIEW_BILLING,
                Feature.UPDATE_PASSWORD,
                Feature.VIEW_ROOMS,
                Feature.VIEW_RESERVATIONS,
                Feature.VIEW_ACTIVITY_LOGS
        ));
    }

    // Get all features assigned to a role (role string converted to lowercase)
    public static Set<Feature> getFeaturesByRole(String role) {
        if (role == null) return Collections.emptySet();
        return roleFeatures.getOrDefault(role.toLowerCase(), Collections.emptySet());
    }

    // Check if a role has a specific feature
    public static boolean hasFeature(String role, Feature feature) {
        if (role == null || feature == null) return false;
        return getFeaturesByRole(role).contains(feature);
    }
}