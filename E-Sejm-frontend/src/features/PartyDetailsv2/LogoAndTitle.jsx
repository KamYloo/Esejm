import React from "react";

const LogoAndTitle = () => (
    <div className="flex items-center space-x-4">
        <div className="w-96 h-44 bg-gradient-to-tl from-blue-400 to-blue-800 rounded-lg flex items-center justify-center">
            <img
                src="https://s5.tvp.pl/images2/e/d/c/uid_edcda90b08c04302bd094e3e52da3e5c_width_2559_play_0_pos_0_gs_0_height_1313.jpg"
                alt="Logo partii"
                className="object-contain rounded-lg"
            />
        </div>
        <div>
            <h1 className="text-4xl text-center font-bold text-white p-1">Konfederacja</h1>
            <p className="text-3xl text-center font-bold text-red-400 font-medium p-1">
                Wolność i Niepodległość
            </p>
        </div>
    </div>
);

export default LogoAndTitle;
