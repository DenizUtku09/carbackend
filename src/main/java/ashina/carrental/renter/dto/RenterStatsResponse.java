package ashina.carrental.renter.dto;

import java.util.List;

public record RenterStatsResponse(
        long activeListings,
        long totalViews,
        long unreadMessages,
        long totalMessages,
        double avgViewsPerListing,
        List<TopListing> topListings
) {
    public record TopListing(Long id, String title, int viewCount) {}
}
