// src/FileUpload.tsx
import React, { useState } from 'react';
import axios from 'axios';
import { Box, Button, Typography, Paper, styled } from '@mui/material';
import DropZone from './DropZone';

const FileUploadContainer = styled(Paper)(({ theme }) => ({
  backgroundColor: '#98AFC7',
  borderRadius: theme.shape.borderRadius,
  padding: theme.spacing(6),
  boxShadow: theme.shadows[5],
  display: 'flex',
  flexDirection: 'column',
  alignItems: 'center',
  justifyContent: 'center',
  width: '100%',
  maxWidth: '600px', // Adjust the max width as needed
}));

const FileUpload: React.FC = () => {
  const [files, setFiles] = useState<File[]>([]);
  const [uploading, setUploading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const handleFileSelect = (newFiles: File[]) => {
    if (files.length < 2) {
      setFiles((prevFiles) => [...prevFiles, ...newFiles]);
    }
  };

  const handleUpload = async () => {
    if (files.length !== 2) {
      setError('Please upload exactly two files.');
      return;
    }

    const formData = new FormData();
    formData.append('user_data', files[0]);
    formData.append('actual_data', files[1]);

    setUploading(true);
    setError(null);

    try {
      await axios.post('http://localhost:9091/api/upload', formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      });
      alert('Files uploaded successfully!');
      setFiles([]);
    } catch (err) {
      setError('');
    } finally {
      setUploading(false);
    }
  };

  return (
    <FileUploadContainer>
      <Typography variant="h4" gutterBottom>
        Upload Excel Files
      </Typography>
      <Box display="flex" flexDirection="column" alignItems="center" gap={2}>
        <DropZone onFileSelect={handleFileSelect} label="Upload Requirement Sheet" />
        <DropZone onFileSelect={handleFileSelect} label="Upload Data to Send" />
        <Button
          variant="contained"
          color="primary"
          onClick={handleUpload}
          disabled={uploading || files.length !== 2}
          sx={{ mt: 2 }}
        >
          {uploading ? 'Uploading...' : 'Send Files'}
        </Button>
        {error && <Typography color="error" mt={2}>{error}</Typography>}
      </Box>
    </FileUploadContainer>
  );
};

export default FileUpload;
