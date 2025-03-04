import React, { useState, useEffect } from "react";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import { AdapterDateFns } from "@mui/x-date-pickers/AdapterDateFns";
import { StaticDatePicker, PickersDay } from "@mui/x-date-pickers";
import { ThemeProvider, Tooltip } from "@mui/material";
import { format } from "date-fns";
import { useDispatch, useSelector } from "react-redux";
import { getProceedingsDatesAction } from "../redux/ProceedingService/Action.js";
import { createTheme } from "@mui/material/styles";
import { useNavigate } from "react-router-dom";

const EventCalendar = () => {
    const [selectedDate, setSelectedDate] = useState(new Date());
    const { proceeding } = useSelector((store) => store);
    const dispatch = useDispatch();
    const navigate = useNavigate();

    useEffect(() => {
        const currentDate = new Date();
        dispatch(getProceedingsDatesAction(currentDate.getFullYear(), currentDate.getMonth() + 1));
    }, [dispatch]);

    const newTheme = (theme) =>
        createTheme({
            ...theme,
            components: {
                MuiDateCalendar: {
                    styleOverrides: {
                        root: {
                            color: "#1565c0",
                            borderRadius: "15px",
                            borderColor: "#2196f3",
                            backgroundColor: "#d6eaf8",
                        },
                    },
                },
            },
        });

    const EventDay = (props) => {
        const { day, outsideCurrentMonth, ...other } = props;
        const formattedDay = format(day, "yyyy-MM-dd");
        const event = proceeding?.dates.find((e) => e.date === formattedDay);
        const isEventDay = Boolean(event);

        return (
            <Tooltip title={isEventDay ? event.proceeding.title : ""} arrow>
                <PickersDay
                    {...other}
                    outsideCurrentMonth={outsideCurrentMonth}
                    day={day}
                    selected={isEventDay}
                    sx={{
                        backgroundColor: isEventDay ? "#1976d2" : "transparent",
                        borderRadius: "50%",
                        color: isEventDay ? "white" : "inherit",
                        "&:hover": { backgroundColor: isEventDay ? "#1565c0" : "transparent" },
                        cursor: "pointer",
                    }}
                    onClick={() => {
                        if (isEventDay) {
                            navigate(`/posiedzenie/${event.proceeding.id}/date/${formattedDay}`);
                        }
                    }}
                />
            </Tooltip>
        );
    };

    return (
        <LocalizationProvider dateAdapter={AdapterDateFns}>
            <ThemeProvider theme={newTheme}>
                <StaticDatePicker
                    displayStaticWrapperAs="desktop"
                    value={selectedDate}
                    onChange={(newValue) => setSelectedDate(newValue)}
                    onMonthChange={(newDate) => {
                        dispatch(getProceedingsDatesAction(newDate.getFullYear(), newDate.getMonth() + 1));
                    }}
                    slots={{
                        day: EventDay,
                    }}
                />
            </ThemeProvider>
        </LocalizationProvider>
    );
};

export default EventCalendar;
