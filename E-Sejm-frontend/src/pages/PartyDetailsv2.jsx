import React from "react";
import LogoAndTitle from "../features/PartyDetailsv2/LogoAndTitle";
import Description from "../features/PartyDetailsv2/Description";
import Leaders from "../features/PartyDetailsv2/Leaders";
import InfoSection from "../features/PartyDetailsv2/InfoSection";
import NewsSection from "../features/PartyDetailsv2/NewsSection";
import StatisticsSection from "../features/PartyDetailsv2/StatisticsSection";
import DemographicsSection from "../features/PartyDetailsv2/DemographicsSection";

const PartyDetailsV2 = () => {
    return (
        <div className="min-h-screen bg-gray-100 pt-20">
            <div className="container mx-auto max-w-7xl px-4">
                {/* Page Header */}
                <header className="text-5xl font-bold flex items-center space-x-4 mb-8">
                    <h2 className="text-blue-600">Szczegóły</h2>Partii
                    <div className="flex-grow border-t-8 border-blue-300"></div>
                </header>

                {/* Header Section */}
                <div className="bg-gradient-to-tl from-blue-400 to-blue-800 shadow-xl rounded-lg p-6 mb-8">
                    <div className="flex items-center justify-between">
                        <LogoAndTitle />
                        <Description />
                    </div>
                </div>

                {/* Leader Section */}
                <Leaders />

                {/* Info Section */}
                <InfoSection />

                {/* News Section */}
                <NewsSection />

                {/* Statistics Section */}
                <StatisticsSection />

                {/* Demographics Section */}
                <DemographicsSection />
            </div>
        </div>
    );
};

export default PartyDetailsV2;
