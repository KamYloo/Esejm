import React, { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useParams } from "react-router-dom";
import { format } from "date-fns";
import { getStatementByIdAction, getStatementTextByIdAction } from "../redux/StatementService/Action.js";

const StatementDetails = () => {
    const { proceedingId, date, statementNum, statementId } = useParams();
    const dispatch = useDispatch();
    const { statement } = useSelector(store => store);

    const [speechSynthesisState, setSpeechSynthesisState] = useState({
        currentIndex: 0,
        isPaused: false,
        chunks: [],
    });

    const synth = window.speechSynthesis;

    const prepareChunks = (htmlText) => {
        const plainText = htmlText.replace(/<[^>]*>/g, "");
        return plainText.split(/(?<=,\s|\.\s)/);
    };

    const startReading = () => {
        if (!statement?.text) {
            alert("Nie ma tekstu do przeczytania.");
            return;
        }

        const chunks = prepareChunks(statement.text);
        setSpeechSynthesisState({ currentIndex: 0, isPaused: false, chunks });
        readChunk(0, chunks);
    };

    const readChunk = (index, chunks) => {
        if (index >= chunks.length) {
            console.log("Odczyt zakończony.");
            return;
        }

        const utterance = new SpeechSynthesisUtterance(chunks[index]);
        utterance.lang = "pl-PL";
        utterance.rate = 1;

        utterance.onend = () => {
            if (!speechSynthesisState.isPaused) {
                setSpeechSynthesisState((prev) => ({
                    ...prev,
                    currentIndex: index + 1,
                }));
                readChunk(index + 1, chunks);
            }
        };

        synth.speak(utterance);
    };

    const pauseReading = () => {
        synth.pause();
        setSpeechSynthesisState((prev) => ({ ...prev, isPaused: true }));
    };

    const resumeReading = () => {
        synth.resume();
        setSpeechSynthesisState((prev) => ({ ...prev, isPaused: false }));
    };

    const skipForward = () => {
        if (speechSynthesisState.currentIndex + 1 < speechSynthesisState.chunks.length) {
            synth.cancel();
            const nextIndex = speechSynthesisState.currentIndex + 1;
            setSpeechSynthesisState((prev) => ({
                ...prev,
                currentIndex: nextIndex,
                isPaused: false,
            }));
            readChunk(nextIndex, speechSynthesisState.chunks);
        }
    };

    const skipBackward = () => {
        if (speechSynthesisState.currentIndex > 0) {
            synth.cancel();
            const prevIndex = speechSynthesisState.currentIndex - 1;
            setSpeechSynthesisState((prev) => ({
                ...prev,
                currentIndex: prevIndex,
                isPaused: false,
            }));
            readChunk(prevIndex, speechSynthesisState.chunks);
        }
    };

    const renderHighlightedText = () => {
        const { chunks, currentIndex } = speechSynthesisState;

        return chunks.map((chunk, index) => (
            <span
                key={index}
                style={{
                    backgroundColor: index === currentIndex ? "#256d9f" : "transparent",
                    color: index === currentIndex ? "#f1faee" : "#ffffff",
                    fontWeight: index === currentIndex ? "bold" : "normal",
                }}
            >
                {chunk}
            </span>
        ));
    };

    useEffect(() => {
        dispatch(getStatementByIdAction(statementId));
        dispatch(getStatementTextByIdAction(proceedingId, statementNum, date));
    }, [dispatch, statementId, date, proceedingId, statementNum]);

    const formatDate = (dateString) => {
        if (!dateString) return "Brak";
        return format(new Date(dateString), "dd.MM.yyyy HH:mm");
    };

    return (
        <div className="max-w-6xl mx-auto p-6 rounded-lg">
            <div className="bg-[#061D31] text-white py-6 px-4 rounded-t-lg">
                <h1 className="text-4xl font-bold">Szczegóły wypowiedzi</h1>
            </div>
            <div className="bg-[#0A2740] p-6 rounded-b-lg shadow-inner">
                <div className="mb-6 flex justify-between gap-4">
                    <div className="w-1/2 text-lg text-gray-200">
                        <p>
                            <span className="font-semibold">Mówca:</span> {statement.data?.name || "Nieznany"}
                        </p>
                        <p>
                            <span className="font-semibold">Funkcja:</span> {statement.data?.function || "Brak"}
                        </p>
                        <p>
                            <span
                                className="font-semibold">Nie wypowiedziane:</span> {statement.data?.unspoken ? "Tak" : "Nie"}
                        </p>
                        <p>
                            <span
                                className="font-semibold">Raportujący:</span> {statement.data?.rapporteur ? "Tak" : "Nie"}
                        </p>
                        <p>
                            <span
                                className="font-semibold">Sekretarz:</span> {statement.data?.secretary ? "Tak" : "Nie"}
                        </p>
                    </div>

                    <div className="w-1/2 text-lg text-gray-200 text-right">
                        <p>
                            <span
                                className="font-semibold">Data rozpoczęcia:</span> {formatDate(statement.data?.startDateTime)}
                        </p>
                        <p>
                            <span
                                className="font-semibold">Data zakończenia:</span> {formatDate(statement.data?.endDateTime)}
                        </p>
                    </div>
                </div>


                <h2 className="text-2xl font-bold text-white mb-4">Treść wypowiedzi</h2>
                <div className="bg-[#061D31] p-4 rounded-lg border border-gray-600 overflow-y-scroll h-96">
                    {renderHighlightedText()}
                </div>

                <div className="flex gap-4 mt-6">
                    <button
                        onClick={startReading}
                        className="px-4 py-2 bg-[#1B3A57] text-white rounded hover:bg-[#1E496A]"
                    >
                        Start
                    </button>
                    <button
                        onClick={pauseReading}
                        className="px-4 py-2 bg-[#5C677D] text-white rounded hover:bg-[#6B788E]"
                    >
                        Pauza
                    </button>
                    <button
                        onClick={resumeReading}
                        className="px-4 py-2 bg-[#2D5B86] text-white rounded hover:bg-[#336A9A]"
                    >
                        Wznów
                    </button>
                    <button
                        onClick={skipBackward}
                        className="px-4 py-2 bg-[#374D68] text-white rounded hover:bg-[#415C7B]"
                    >
                        Wstecz
                    </button>
                    <button
                        onClick={skipForward}
                        className="px-4 py-2 bg-[#374D68] text-white rounded hover:bg-[#415C7B]"
                    >
                        Dalej
                    </button>
                </div>
            </div>
        </div>
    );
};

export default StatementDetails;
