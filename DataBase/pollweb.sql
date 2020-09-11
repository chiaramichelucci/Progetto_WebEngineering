-- phpMyAdmin SQL Dump
-- version 4.9.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Ago 24, 2020 alle 15:37
-- Versione del server: 10.4.8-MariaDB
-- Versione PHP: 7.3.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `pollweb`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `amministratore`
--

CREATE TABLE `amministratore` (
  `id` int(11) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16;

--
-- Dump dei dati per la tabella `amministratore`
--

INSERT INTO `amministratore` (`id`, `email`, `password`) VALUES
(1, 'administrator@pollweb.it', 'administrator');

-- --------------------------------------------------------

--
-- Struttura della tabella `domanda`
--

CREATE TABLE `domanda` (
  `codice` int(11) NOT NULL,
  `testo` varchar(250) NOT NULL,
  `nota` varchar(250) NOT NULL,
  `tipo` varchar(8) DEFAULT NULL,
  `obbligatoria` tinyint(1) NOT NULL,
  `sondaggio` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16;

--
-- Dump dei dati per la tabella `domanda`
--

INSERT INTO `domanda` (`codice`, `testo`, `nota`, `tipo`, `obbligatoria`, `sondaggio`) VALUES
(1, 'Il tuo nome?', 'Inserire grazie.', NULL, 1, 1),
(2, 'Che voto dai a questo progetto?', '', NULL, 1, 2),
(3, 'Seleziona il sesso.', '', NULL, 1, 1),
(4, 'Hai suggerimenti?', 'scrivili sotto', NULL, 1, 2);

-- --------------------------------------------------------

--
-- Struttura della tabella `interagisce`
--

CREATE TABLE `interagisce` (
  `utente` int(11) NOT NULL,
  `sondaggio` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16;

-- --------------------------------------------------------

--
-- Struttura della tabella `opzione`
--

CREATE TABLE `opzione` (
  `codice_domanda` int(11) NOT NULL,
  `testo` varchar(256) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struttura della tabella `risposta`
--

CREATE TABLE `risposta` (
  `codice_domanda` int(11) NOT NULL,
  `id_utente` int(11) NOT NULL,
  `id_sondaggio` int(11) NOT NULL,
  `testo` varchar(256) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struttura della tabella `sondaggio`
--

CREATE TABLE `sondaggio` (
  `id` int(11) NOT NULL,
  `titolo` varchar(100) NOT NULL,
  `disponibile` tinyint(1) NOT NULL,
  `modalita` varchar(10) NOT NULL,
  `url` varchar(256) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16;

--
-- Dump dei dati per la tabella `sondaggio`
--

INSERT INTO `sondaggio` (`id`, `titolo`, `disponibile`, `modalita`, `url`) VALUES
(1, 'Test', 1, '', ''),
(2, 'Test2', 1, '', '');

-- --------------------------------------------------------

--
-- Struttura della tabella `utente`
--

CREATE TABLE `utente` (
  `id` int(11) NOT NULL,
  `cognome` varchar(25) NOT NULL,
  `nome` varchar(25) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `tipo` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16;

--
-- Dump dei dati per la tabella `utente`
--

INSERT INTO `utente` (`id`, `cognome`, `nome`, `email`, `password`, `tipo`) VALUES
(1, 'michelucci', 'chiara', 'chiara@pollweb.it', 'chiara', 'responsabile'),
(2, 'stratulat', 'dragos', 'dragos@pollweb.it', 'dragos', 'responsabile');

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `amministratore`
--
ALTER TABLE `amministratore`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `domanda`
--
ALTER TABLE `domanda`
  ADD PRIMARY KEY (`codice`),
  ADD KEY `sondaggio` (`sondaggio`);

--
-- Indici per le tabelle `interagisce`
--
ALTER TABLE `interagisce`
  ADD KEY `utente` (`utente`),
  ADD KEY `sondaggio` (`sondaggio`);

--
-- Indici per le tabelle `opzione`
--
ALTER TABLE `opzione`
  ADD PRIMARY KEY (`codice_domanda`);

--
-- Indici per le tabelle `risposta`
--
ALTER TABLE `risposta`
  ADD PRIMARY KEY (`codice_domanda`,`id_utente`,`id_sondaggio`),
  ADD KEY `risposta_fbk_2` (`id_sondaggio`),
  ADD KEY `risposta_fbk_3` (`id_utente`);

--
-- Indici per le tabelle `sondaggio`
--
ALTER TABLE `sondaggio`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `utente`
--
ALTER TABLE `utente`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `amministratore`
--
ALTER TABLE `amministratore`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT per la tabella `domanda`
--
ALTER TABLE `domanda`
  MODIFY `codice` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT per la tabella `opzione`
--
ALTER TABLE `opzione`
  MODIFY `codice_domanda` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT per la tabella `sondaggio`
--
ALTER TABLE `sondaggio`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT per la tabella `utente`
--
ALTER TABLE `utente`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `domanda`
--
ALTER TABLE `domanda`
  ADD CONSTRAINT `domanda_ibfk_1` FOREIGN KEY (`sondaggio`) REFERENCES `sondaggio` (`id`) ON UPDATE CASCADE;

--
-- Limiti per la tabella `interagisce`
--
ALTER TABLE `interagisce`
  ADD CONSTRAINT `interagisce_ibfk_1` FOREIGN KEY (`sondaggio`) REFERENCES `sondaggio` (`id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `interagisce_ibfk_2` FOREIGN KEY (`utente`) REFERENCES `utente` (`id`) ON UPDATE CASCADE;

--
-- Limiti per la tabella `opzione`
--
ALTER TABLE `opzione`
  ADD CONSTRAINT `opzione_fbk_1` FOREIGN KEY (`codice_domanda`) REFERENCES `domanda` (`codice`) ON UPDATE CASCADE;

--
-- Limiti per la tabella `risposta`
--
ALTER TABLE `risposta`
  ADD CONSTRAINT `risposta_fbk_1` FOREIGN KEY (`codice_domanda`) REFERENCES `domanda` (`codice`) ON UPDATE CASCADE,
  ADD CONSTRAINT `risposta_fbk_2` FOREIGN KEY (`id_sondaggio`) REFERENCES `sondaggio` (`id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `risposta_fbk_3` FOREIGN KEY (`id_utente`) REFERENCES `utente` (`id`) ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
