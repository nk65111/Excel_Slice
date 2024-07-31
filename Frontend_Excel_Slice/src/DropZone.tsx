// src/DropZone.tsx
import React, { useCallback, useState } from 'react';
import { Typography, Paper, styled } from '@mui/material';

const DropZoneContainer = styled(Paper)(({ theme }) => ({
  padding: theme.spacing(2),
  textAlign: 'center',
  border: '2px dashed #ccc',
  borderRadius: theme.shape.borderRadius,
  cursor: 'pointer',
  '&:hover': {
    backgroundColor: theme.palette.action.hover,
  },
}));

interface DropZoneProps {
  onFileSelect: (files: File[]) => void;
  label: string;
}

const DropZone: React.FC<DropZoneProps> = ({ onFileSelect, label }) => {
  const [dragging, setDragging] = useState(false);

  const handleDrop = useCallback((event: React.DragEvent) => {
    event.preventDefault();
    setDragging(false);
    const files = event.dataTransfer.files;
    if (files.length) {
      onFileSelect(Array.from(files));
    }
  }, [onFileSelect]);

  const handleDragOver = (event: React.DragEvent) => {
    event.preventDefault();
    setDragging(true);
  };

  const handleDragLeave = () => {
    setDragging(false);
  };

  const handleFileChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    if (event.target.files) {
      onFileSelect(Array.from(event.target.files));
    }
  };

  return (
    <DropZoneContainer
      onDrop={handleDrop}
      onDragOver={handleDragOver}
      onDragLeave={handleDragLeave}
    >
      <input
        type="file"
        multiple
        style={{ display: 'none' }}
        onChange={handleFileChange}
        id="file-input"
      />
      <label htmlFor="file-input">
        <Typography variant="h6" color="textSecondary">
          {label}
        </Typography>
        {dragging && <Typography variant="body2" color="textSecondary">Drop files here</Typography>}
      </label>
    </DropZoneContainer>
  );
};

export default DropZone;
