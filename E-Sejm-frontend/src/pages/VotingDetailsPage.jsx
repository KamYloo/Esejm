import React, { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useParams } from "react-router-dom";
import { PieChart } from "@mui/x-charts/PieChart";
import { getVotingDetailsAction, getVotesByVotingAction } from "../redux/VotingService/Action";
import PoliticianList from "../features/politicians/PoliticianList";

const VotingDetailsPage = () => {
    const { votingId } = useParams();
    const dispatch = useDispatch();
    const { votingDetails, votes } = useSelector((state) => state.voting);

    const [filter, setFilter] = useState("");
    const [partyFilter, setPartyFilter] = useState("");
    const [selectedVoteFilter, setSelectedVoteFilter] = useState("");

    useEffect(() => {
        dispatch(getVotingDetailsAction(votingId));
        dispatch(getVotesByVotingAction(votingId));
    }, [dispatch, votingId]);

    const handleFilterChange = (e) => setFilter(e.target.value);
    const handlePartyFilterChange = (e) => setPartyFilter(e.target.value);
    const handleVoteFilterChange = (filter) => setSelectedVoteFilter(filter);

    const getPartyVoteData = () => {
        if (!votes) return [];
        const partyData = {};

        votes?.content.forEach((vote) => {
            if (!partyData[vote.mp.club]) {
                partyData[vote.mp.club] = { YES: 0, NO: 0, ABSTAIN: 0, ABSENT: 0 };
            }
            partyData[vote.mp.club][vote.vote]++;
        });

        return Object.entries(partyData).map(([party, data]) => ({
            party,
            data: [
                { id: "YES", label: "Za", value: data.YES, color: "#34D399" },
                { id: "NO", label: "Przeciw", value: data.NO, color: "#F87171" },
                { id: "ABSTAIN", label: "Wstrzymał się", value: data.ABSTAIN, color: "#A78BFA" },
                { id: "ABSENT", label: "Nieobecny", value: data.ABSENT, color: "#9CA3AF" },
            ].filter((item) => item.value > 0),
        }));
    };

    const partyVoteData = getPartyVoteData();

    const filteredVotes = votes?.content?.filter((vote) => {
        if (selectedVoteFilter && vote.vote !== selectedVoteFilter) return false;
        if (partyFilter && !vote.mp.club.toLowerCase().includes(partyFilter.toLowerCase())) return false;
        const fullName = `${vote.mp.firstName} ${vote.mp.lastName}`.toLowerCase();
        return fullName.includes(filter.toLowerCase());
    });

    return (
        <div className="min-h-screen bg-gray-100 pt-20">
            <div className="container mx-auto max-w-6xl px-6 py-8 bg-white shadow-lg rounded-lg">
                <h1 className="text-4xl font-extrabold mb-6 text-blue-600">{votingDetails?.title}</h1>
                <p className="text-lg mb-4">{votingDetails?.description}</p>
                <p className="text-md mb-2">
                    <strong>Data głosowania:</strong> {new Date(votingDetails?.date).toLocaleString()}
                </p>
                <p className="text-md mb-2">
                    <strong>Liczba głosów:</strong> {votingDetails?.totalVoted}
                </p>
                <p className="text-md mb-2">
                    <strong>Za:</strong> {votingDetails?.yesVotes}, <strong>Przeciw:</strong> {votingDetails?.noVotes},{" "}
                    <strong>Wstrzymał się:</strong> {votingDetails?.abstain}
                </p>
                <p className="text-md mb-6">
                    <strong>Nieobecni:</strong> {votingDetails?.notParticipating}
                </p>

                {/* Wykresy dla partii */}
                <h2 className="text-2xl font-bold text-center mb-4">Wyniki głosowania według partii</h2>
                <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8 mb-12">
                    {partyVoteData.map(({ party, data }) => (
                        <div
                            key={party}
                            className="bg-gray-50 p-6 rounded-lg shadow-md flex flex-col items-center justify-center"
                            style={{ height: "200px" }}
                        >
                            <h3 className="text-lg font-bold text-center mb-4">{party}</h3>
                            <div className="flex justify-center items-center h-full">
                                {data.length > 0 ? (
                                    <PieChart
                                        width={200}
                                        height={200}
                                        series={[
                                            {
                                                data,
                                                innerRadius: 45,
                                                outerRadius: 70,
                                                startAngle: 0,
                                                endAngle: 360,
                                                colorAccessor: (dataPoint) => dataPoint.color,
                                            },
                                        ]}
                                        slotProps={{
                                            legend: {
                                                hidden: true, // Legend removed
                                            },
                                        }}
                                    />
                                ) : (
                                    <p className="text-gray-500">Brak danych</p>
                                )}
                            </div>
                        </div>
                    ))}
                </div>

                {/* Filtry */}
                <div className="flex flex-wrap items-center justify-between bg-gray-50 p-4 rounded-lg shadow-md mb-8 space-y-4 md:space-y-0">
                    <input
                        type="text"
                        placeholder="Wyszukaj posła"
                        value={filter}
                        onChange={handleFilterChange}
                        className="flex-grow p-3 border rounded-lg focus:outline-none focus:ring focus:border-blue-300 mr-4"
                    />
                    <input
                        type="text"
                        placeholder="Wyszukaj po partii"
                        value={partyFilter}
                        onChange={handlePartyFilterChange}
                        className="flex-grow p-3 border rounded-lg focus:outline-none focus:ring focus:border-blue-300 mr-4"
                    />
                    <div className="flex space-x-2">
                        <button
                            onClick={() => handleVoteFilterChange("")}
                            className={`px-4 py-2 rounded-lg ${
                                !selectedVoteFilter ? "bg-blue-500 text-white" : "bg-gray-300"
                            } hover:bg-blue-600`}
                        >
                            Wszystkie
                        </button>
                        <button
                            onClick={() => handleVoteFilterChange("YES")}
                            className={`px-4 py-2 rounded-lg ${
                                selectedVoteFilter === "YES" ? "bg-blue-500 text-white" : "bg-gray-300"
                            } hover:bg-blue-600`}
                        >
                            Za
                        </button>
                        <button
                            onClick={() => handleVoteFilterChange("NO")}
                            className={`px-4 py-2 rounded-lg ${
                                selectedVoteFilter === "NO" ? "bg-blue-500 text-white" : "bg-gray-300"
                            } hover:bg-blue-600`}
                        >
                            Przeciw
                        </button>
                        <button
                            onClick={() => handleVoteFilterChange("ABSTAIN")}
                            className={`px-4 py-2 rounded-lg ${
                                selectedVoteFilter === "ABSTAIN" ? "bg-blue-500 text-white" : "bg-gray-300"
                            } hover:bg-blue-600`}
                        >
                            Wstrzymał się
                        </button>
                        <button
                            onClick={() => handleVoteFilterChange("ABSENT")}
                            className={`px-4 py-2 rounded-lg ${
                                selectedVoteFilter === "ABSENT" ? "bg-blue-500 text-white" : "bg-gray-300"
                            } hover:bg-blue-600`}
                        >
                            Nieobecny
                        </button>
                    </div>
                </div>

                {/* Lista posłów */}
                <PoliticianList votes={filteredVotes || []} />
            </div>
        </div>
    );
};

export default VotingDetailsPage;
