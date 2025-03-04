import React, {forwardRef} from "react";
import {useNavigate} from "react-router-dom";

// eslint-disable-next-line react/display-name
const PoliticianCard = forwardRef(({ deputy }, ref) => {

    const navigate = useNavigate();

    return (
        <div
            ref={ref}
            className="bg-white p-6 rounded-lg shadow-lg flex items-center transition-transform duration-300 hover:scale-105 hover:shadow-2xl cursor-pointer"
            onClick={() => navigate(`/politician-profile/${deputy?.id}`)}
        >
            <div className="w-24 h-24 rounded-full bg-gray-300 flex-shrink-0 mr-6">
                <img
                    src={deputy?.photo}
                    alt={`${deputy?.firstName} ${deputy?.lastName}`}
                    className="w-full h-full rounded-full object-cover"
                />
            </div>
            <div className="flex flex-col">
                <h3 className="text-2xl font-semibold">
                    {deputy?.firstName} {deputy?.secondName} {deputy?.lastName}
                </h3>
                <div className="flex items-center mt-3">
                    <div
                        className="w-10 h-10 rounded bg-blue-500 flex items-center justify-center text-white font-bold mr-3">
                        K
                    </div>
                    <span className="text-gray-600 text-lg">{deputy?.club}</span>
                </div>
            </div>
        </div>
    );
});

export {PoliticianCard};
